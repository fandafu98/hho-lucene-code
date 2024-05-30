/**
 * * @author: 郑延康 * @date: 2024-05-28 21:36
 */
package com.hho.framework.runner;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import com.hho.framework.constant.DocumentFieldConstant;
import com.hho.framework.constant.MokeDataConstant;
import com.hho.framework.util.LuceneUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.lucene.document.*;
import org.apache.lucene.index.IndexWriter;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;

/**
 * @author zhengyankang * @version InitIndexDataRunner.java, v 0.1 2024-05-28 21:36
 */
@Slf4j
@Component
public class InitIndexDataRunner implements ApplicationRunner {

    @Override
    public void run(ApplicationArguments args) {
        IndexWriter indexWriter = LuceneUtil.indexWriter();
        try {
            log.info("=========== 开始初始化lucene内的数据");

            indexWriter.deleteAll();
            // 今日时间
            Date today = new Date();
            for (int i = 1; i <= 100; i++) {
                Document document = new Document();
                document.add(new LongField(DocumentFieldConstant.ID, Long.valueOf(i), Field.Store.YES));

                // 取模，用于生成随机内容的索引
                int index_name = RandomUtil.randomInt(0, 5);
                int index_type = RandomUtil.randomInt(0, 5);
                int index_camera = RandomUtil.randomInt(0, 5);
                int index_screen = RandomUtil.randomInt(0, 5);
                int status_random = RandomUtil.randomInt(0, 5);

                String title = String.format(MokeDataConstant.TITLE_TEMPLATE,
                        MokeDataConstant.TITLE_TEMPLATE_NAME[index_name],
                        MokeDataConstant.TITLE_TEMPLATE_TYPE[index_type],
                        MokeDataConstant.TITLE_TEMPLATE_CAMERA[index_camera],
                        MokeDataConstant.TITLE_TEMPLATE_SCREEN[index_screen]);

                document.add(new TextField(DocumentFieldConstant.TITLE, title, Field.Store.YES));
                document.add(new StringField(DocumentFieldConstant.STATUS, MokeDataConstant.STATUS_RANDOM[status_random], Field.Store.YES));
                // 日期从今天开始，2024-05-28 往后偏100天
                document.add(new LongField(DocumentFieldConstant.TIME, DateUtil.offset(today, DateField.DAY_OF_YEAR, i).getTime(), Field.Store.YES));
                indexWriter.addDocument(document);
            }
            indexWriter.commit();
        } catch (IOException e) {
            log.error("发生异常", e);
            throw new RuntimeException(e);
        } finally {
            try {
                if (null != indexWriter) {
                    indexWriter.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        log.info("=========== lucene数据初始化完成");
    }


}
