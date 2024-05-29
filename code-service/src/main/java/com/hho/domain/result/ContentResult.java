/**
 * * @author: 郑延康 * @date: 2024-05-28 21:31 */
package com.hho.domain.result;

import lombok.Data;

/** * @author zhengyankang * @version ContentDO.java, v 0.1 2024-05-28 21:31 */
@Data
public class ContentResult {

    /**
     * 业务主键
     */
    private Long id;

    /**
     * 标题内容
     */
    private String title;

    /**
     * 状态
     */
    private String status;

    /**
     * 日期时间
     */
    private String time;

}
