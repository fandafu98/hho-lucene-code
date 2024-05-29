/**
 * * @author: 郑延康 * @date: 2024-05-28 22:33
 */
package com.hho.service;


import com.hho.domain.page.PageParam;
import com.hho.domain.page.PageResult;
import com.hho.domain.param.SearchParam;
import com.hho.domain.param.UpdateBatchParam;
import com.hho.domain.result.ContentResult;

import java.util.List;

/**
 * @author zhengyankang * @version LuceneDemoManager.java, v 0.1 2024-05-28 22:33
 */
public interface LuceneDemoManager {

    /**
     * 分页检索满足条件的doc
     *
     * @param pageParam
     * @return
     */
    PageResult<ContentResult> pageQuery(PageParam<SearchParam> pageParam);

    /**
     * 查询满足条件的文档数
     *
     * @param searchParam
     * @return
     */
    Integer getDocsCount(SearchParam searchParam);


    /**
     * 基于id增量批量更新索引
     *
     * @param paramList
     */
    void updateBatch(List<UpdateBatchParam> paramList);


}
