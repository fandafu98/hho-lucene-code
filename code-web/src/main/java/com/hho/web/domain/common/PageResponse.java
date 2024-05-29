package com.hho.web.domain.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/** * 分页结果通用类 * */
@Data
@ApiModel(value = "分页结果通用类")
public class PageResponse<T> {

    /**
     * 记录
     */
    @ApiModelProperty(value = "记录")
    List<T> records;

    /**
     * 总条数
     */
    @ApiModelProperty(value = "总条数")
    Long total;

    /**
     * 页码
     */
    @ApiModelProperty(value = "页码")
    Long current;

    /**
     * 每页数量
     */
    @ApiModelProperty(value = "每页数量")
    Long size;

}
