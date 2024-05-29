/**
 * * @author: 郑延康 * @date: 2024-05-28 22:23 */
package com.hho.web.domain.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/** * @author zhengyankang * @version SearchRequest.java, v 0.1 2024-05-28 22:23 */
@Data
public class SearchRequest {

    /**
     * 排序规则 false.升序 true.倒序 （默认正序）
     */
    @ApiModelProperty("排序规则 false.升序 true.倒序 （默认正序）")
    private Boolean isDesc = false;

    /**
     * 标题模糊查询
     */
    @ApiModelProperty("标题模糊查询")
    private String title;

    /**
     * 状态查询列表
     */
    @ApiModelProperty("状态查询列表")
    private List<String> statusList;

    /**
     * 开始时间
     */
    @ApiModelProperty("开始时间")
    private String startTime;

    /**
     * 结束时间
     */
    @ApiModelProperty("结束时间")
    private String endTime;

}
