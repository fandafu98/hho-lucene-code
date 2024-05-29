/**
 * * @author: 郑延康 * @date: 2024-05-28 21:31 */
package com.hho.web.domain.response;

import cn.hutool.core.date.DateUtil;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/** * @author zhengyankang * @version ContentDO.java, v 0.1 2024-05-28 21:31 */
@Data
public class ContentResponse {

    /**
     * 业务主键
     */
    @ApiModelProperty("业务主键")
    private Long id;

    /**
     * 标题内容
     */
    @ApiModelProperty("标题内容")
    private String title;

    /**
     * 状态
     */
    @ApiModelProperty("状态")
    private String status;

    /**
     * 日期时间
     */
    @ApiModelProperty("日期时间")
    private String time;

}
