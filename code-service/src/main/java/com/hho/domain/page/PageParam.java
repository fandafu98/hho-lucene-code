/* * zhuyanyoushu.com * Copyright (C) 2021-${YEAR} All Rights Reserved. * */
package com.hho.domain.page;

import lombok.Data;

/** * 分页参数通用类 * * @author huhanbo * @version PageParam.java, v 0.1 2021-10-14 17:43 huhanbo */
@Data
public class PageParam<T> {
    /**
     * 请求对象
     */
    T query;
    /**
     * 页码
     */
    private Integer pageNum;
    /**
     * 每页数量
     */
    private Integer pageSize;
}