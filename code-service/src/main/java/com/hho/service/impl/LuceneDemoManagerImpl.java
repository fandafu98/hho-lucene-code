/**
 * * @author: 郑延康 * @date: 2024-05-28 22:33
 */
package com.hho.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import com.hho.domain.page.PageParam;
import com.hho.domain.page.PageResult;
import com.hho.domain.param.SearchParam;
import com.hho.domain.param.UpdateBatchParam;
import com.hho.domain.result.ContentResult;
import com.hho.framework.constant.DocumentFieldConstant;
import com.hho.framework.util.LuceneUtil;
import com.hho.service.LuceneDemoManager;
import com.hho.utils.LockUtil;
import com.hho.utils.ManualPageHelperUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.lucene.document.*;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.*;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.function.Consumer;

/**
 * @author zhengyankang * @version LuceneDemoManagerImpl.java, v 0.1 2024-05-28 22:33
 */
@Slf4j
@Service
public class LuceneDemoManagerImpl implements LuceneDemoManager {

    /**
     * 分页检索满足条件的doc
     *
     * @param pageParam
     * @return
     */
    @Override
    public PageResult<ContentResult> pageQuery(PageParam<SearchParam> pageParam) {

        // 取出查询条件对象
        SearchParam searchParam = pageParam.getQuery();

        IndexReader indexReader = LuceneUtil.indexReader();

        // 每页条数
        int pageSize = pageParam.getPageSize();
        // 当前页码
        int pageNum = pageParam.getPageNum();
        // 当前页的起始条数
        int start = (pageNum - 1) * pageSize;
        // 当前页的结束条数（不能包含）
        int end = start + pageSize;

        // 构建查询条件BooleanQuery
        Query booleanQuery = buildBooleanQuery(searchParam);

        try {
            // 创建排序对象，false.升序。true.降序
            Sort sort = new Sort(new SortField(DocumentFieldConstant.ID, SortField.Type.LONG, searchParam.getIsDesc()));

            TopDocs topDocs = LuceneUtil.indexSearcher().search(booleanQuery, end, sort);
            if (topDocs.totalHits == 0) {
                return ManualPageHelperUtil.startPage(new ArrayList<>(), pageNum, pageSize, topDocs.totalHits);
            }
            // 查询对应的文档
            ScoreDoc[] scoreDocs = topDocs.scoreDocs;
            List<ContentResult> resultList = new ArrayList<>();
            Collection<Document> documentList = new ArrayList<>();
            // 遍历文档结果集
            int lastIndex = end;
            if (topDocs.totalHits < end) {
                lastIndex = topDocs.totalHits;
            }
            for (int i = start; i < lastIndex; i++) {
                ScoreDoc scoreDoc = scoreDocs[i];
                // 获取文档编号
                int documentIdID = scoreDoc.doc;
                // 创建读取工具
                Document document = indexReader.document(documentIdID);
                documentList.add(document);
            }

            // 组装结果集的数据
            documentList.forEach(document -> {
                // 这里本来用反射做了，但发现结果太啰嗦了，反而会绕晕阅读者，所以直接用字段属性赋值的方法
                // 这里暂时用了魔法值，我还没想到更优雅的方法，或者用常量先代替一下吧
                ContentResult contentResult = new ContentResult();
                contentResult.setId(Long.valueOf(document.get(DocumentFieldConstant.ID)));
                contentResult.setTitle(document.get(DocumentFieldConstant.TITLE));
                contentResult.setStatus(document.get(DocumentFieldConstant.STATUS));
                contentResult.setTime(DateUtil.formatDate(new Date(Long.valueOf(document.get(DocumentFieldConstant.TIME)))));
                resultList.add(contentResult);
            });

            return ManualPageHelperUtil.startPage(resultList, pageNum, pageSize, topDocs.totalHits);

        } catch (IOException e) {
            log.error("出现异常{}", e.getMessage());
            throw new RuntimeException(e);
        } finally {
            if (null != indexReader) {
                try {
                    indexReader.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    /**
     * 查询满足条件的文档数
     *
     * @param searchParam
     * @return
     */
    @Override
    public Integer getDocsCount(SearchParam searchParam) {

        try {
            // 构建查询条件BooleanQuery
            Query booleanQuery = buildBooleanQuery(searchParam);

            TopDocs topDocs = LuceneUtil.indexSearcher().search(booleanQuery, Integer.MAX_VALUE);
            return topDocs.scoreDocs.length;

        } catch (IOException e) {
            log.error("获取文档数出现IO异常{}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * 组装通用查询条件
     *
     * @param searchParam 查询对象
     * @return
     */
    private Query buildBooleanQuery(SearchParam searchParam) {

        // 查询器结合
        Collection<Query> queryList = new ArrayList<>();

        // title标题内容查询
        if (StringUtils.isNotBlank(searchParam.getTitle())) {
            Query titleQuery = new TermQuery(new Term(DocumentFieldConstant.TITLE, searchParam.getTitle()));
            queryList.add(titleQuery);
        }

        // status状态多值匹配
        if (CollectionUtil.isNotEmpty(searchParam.getStatusList())) {
            BooleanQuery statusQuery = new BooleanQuery();
            searchParam.getStatusList().forEach(status -> {
                statusQuery.add(new TermQuery(new Term(DocumentFieldConstant.STATUS, status)), BooleanClause.Occur.SHOULD);
            });
            queryList.add(statusQuery);
        }

        // time时间范围查询
        if (StringUtils.isNotBlank(searchParam.getStartTime()) && StringUtils.isNotBlank(searchParam.getEndTime())) {
            Query timeQuery = NumericRangeQuery.newLongRange(
                    DocumentFieldConstant.TIME,
                    DateUtil.parse(searchParam.getStartTime()).getTime(),
                    DateUtil.parse(searchParam.getEndTime()).getTime(),
                    true,
                    true);
            queryList.add(timeQuery);
        }

        // 组装查询器
        BooleanQuery booleanQuery = new BooleanQuery();
        queryList.forEach(query -> {
            booleanQuery.add(query, BooleanClause.Occur.MUST);
        });

        // 如果都没有查询，就默认查所有
        if (CollectionUtil.isEmpty(queryList)) {
            // 默认全值匹配查询
            MatchAllDocsQuery matchAllDocsQuery = new MatchAllDocsQuery();
            return matchAllDocsQuery;
        }

        // 返回
        return booleanQuery;
    }

    /**
     * 基于id增量批量更新索引
     *
     * @param paramList
     */
    @Override
    public void updateBatch(List<UpdateBatchParam> paramList) {

        // 获取写出器
        IndexWriter indexWriter = LuceneUtil.indexWriter();
        // 获取读取器
        IndexReader indexReader = LuceneUtil.indexReader();

        try {
            // 遍历每个新增或更新的对象体
            for (UpdateBatchParam updateBatchParam : paramList) {

                if (StringUtils.isBlank(updateBatchParam.getId())) {
                    throw new RuntimeException("ID不能为空");
                }

                // 根据当前更新或新增的文档ID，获取对应的ReentrantLock，进行锁同步
                LockUtil.lock(updateBatchParam.getId(), () -> {

                    try {
                        // 获取Long查询器
                        Query idQuery = NumericRangeQuery.newLongRange(DocumentFieldConstant.ID, Long.valueOf(updateBatchParam.getId()), Long.valueOf(updateBatchParam.getId()), true, true);
                        // 查询结果集
                        TopDocs topDocs = LuceneUtil.indexSearcher().search(idQuery, 1);

                        // 获取新增或更新文档操作的consumer
                        Consumer<Document> documentConsumer = documentConsumer(topDocs, indexWriter, Long.valueOf(updateBatchParam.getId()));

                        // 创建文档
                        Document document = new Document();
                        document.add(new LongField(DocumentFieldConstant.ID, Long.valueOf(updateBatchParam.getId()), Field.Store.YES));
                        document.add(new TextField(DocumentFieldConstant.TITLE, updateBatchParam.getTitle(), Field.Store.YES));
                        document.add(new StringField(DocumentFieldConstant.STATUS, updateBatchParam.getStatus(), Field.Store.YES));
                        // 日期就是当前时间
                        document.add(new LongField(DocumentFieldConstant.TIME, System.currentTimeMillis(), Field.Store.YES));

                        // 执行新增或更新文档的操作
                        documentConsumer.accept(document);

                        // 提交
                        indexWriter.commit();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                });
            }

        } finally {
            try {
                // 关闭流的操作
                if (null != indexWriter) {
                    indexWriter.close();
                }
                if (null != indexReader) {
                    indexReader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * 获取文档操作的consumer，要么更新，要么删除
     *
     * @param topDocs     本次查询结果集
     * @param indexWriter 写出器
     * @param id          查询对象
     * @return
     */
    Consumer<Document> documentConsumer(TopDocs topDocs, IndexWriter indexWriter, Long id) {
        // 如果结果集长度==0，说明没有该ID对应的记录，为新增
        if (null != topDocs && null != topDocs.scoreDocs && topDocs.scoreDocs.length == 0) {
            return (document) -> {
                try {
                    // 新增文档
                    indexWriter.addDocument(document);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            };
        } else {
            // 否则，就是就更新操作
            return (document) -> {
                try {
                    // 更新文档
                    indexWriter.updateDocument(new Term(DocumentFieldConstant.ID, String.valueOf(id)), document);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            };
        }
    }


}
