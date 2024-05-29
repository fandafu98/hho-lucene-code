/* * zhuyanyoushu.com * Copyright (C) 2021-${YEAR} All Rights Reserved. * */
package com.hho.domain.page;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/** * 分页结果通用类 * * @author huhanbo * @version PageModel.java, v 0.1 2021-10-11 19:13 huhanbo */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageResult<T> {

    /**
     * 记录
     */
    List<T> records;

    /**
     * 总条数
     */
    Long total;

    /**
     * 页码
     */
    Long current;

    /**
     * 每页数量
     */
    Long size;
}