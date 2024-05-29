/**
 * * @author: 郑延康 * @date: 2024-05-29 22:52 */
package com.hho.framework.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.MMapDirectory;
import org.apache.lucene.util.Version;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.File;
import java.io.IOException;

/** * @author zhengyankang * @version LuceneUtil.java, v 0.1 2024-05-29 22:52 */
@Slf4j
public class LuceneUtil {

    public static IndexSearcher indexSearcher = null;


    public static Directory directory() {
        try {
            MMapDirectory mMapDirectory = new MMapDirectory(new File("/Users/zhengyankang/Downloads/lucene_data"));
            return mMapDirectory;
        } catch (IOException e) {
            log.info("发生IO异常,{}", e.getMessage());
            throw new RuntimeException(e);
        }

    }


    public static IndexWriter indexWriter() {
        try {
            Directory directory = directory();
            IndexWriterConfig indexWriterConfig = new IndexWriterConfig(Version.LATEST, new IKAnalyzer());
            indexWriterConfig.setOpenMode(IndexWriterConfig.OpenMode.CREATE_OR_APPEND);
            IndexWriter indexWriter = new IndexWriter(directory, indexWriterConfig);
            return indexWriter;
        } catch (IOException e) {
            log.info("发生IO异常,{}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public static IndexReader indexReader() {
        try {
            Directory directory = directory();
            // 索引读取工具
            IndexReader reader = DirectoryReader.open(directory);
            return reader;
        } catch (IOException e) {
            log.info("发生IO异常,{}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public static IndexSearcher indexSearcher() {
        if (indexSearcher == null) {
            synchronized (LuceneUtil.class) {
                IndexReader indexReader = indexReader();
                // 索引搜索工具
                IndexSearcher searcher = new IndexSearcher(indexReader);
                indexSearcher = searcher;
            }
        }
        return indexSearcher;
    }


}
