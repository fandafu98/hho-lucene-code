package com.hho.web.domain.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/** * 分页请求通用参数 * */
@ApiModel(value = "分页请求通用参数")
@Data
public class PageRequest<T> {

    /**
     * 请求对象
     */
    @ApiModelProperty(value = "请求对象", required = true)
    T query;

    /**
     * 页码
     */
    private Integer pageNum;

    /**
     * 每页数量
     */
    @ApiModelProperty(value = "每页数量", required = true)
    private Integer pageSize;

}
