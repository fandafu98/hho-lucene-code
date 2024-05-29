/**
 * * @author: 郑延康 * @date: 2024-05-29 00:04 */
package com.hho.web.domain.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/** * * * @author zhengyankang * @version UpdateBatchParam.java, v 0.1 2024-05-29 00:04 */
@Data
public class UpdateBatchRequest {

    /**
     * 主键ID
     */
    @ApiModelProperty("主键ID")
    public String id;

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

}
