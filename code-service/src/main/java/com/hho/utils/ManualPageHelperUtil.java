package com.hho.utils;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.PageUtil;
import com.hho.domain.page.PageResult;

import java.util.List;

/** * @author: 郑延康 */
public class ManualPageHelperUtil {


    private ManualPageHelperUtil() {
        throw new UnsupportedOperationException();
    }

    /**
     * 手动分页
     *
     * @param allList  要分页的列表
     * @param pageNum  页码
     * @param pageSize 页大小
     * @param <T>
     * @return
     */
    public static <T> PageResult<T> startPage(List<T> allList, Integer pageNum, Integer pageSize) {
        PageResult<T> pageResult = new PageResult<>();
        final int[] pageArr = PageUtil.transToStartEnd(pageNum - 1, pageSize);
        pageResult.setRecords(CollUtil.sub(allList, pageArr[0], pageArr[1]));
        pageResult.setTotal((long) allList.size());
        pageResult.setSize((long) pageSize);
        pageResult.setCurrent((long) pageNum);
        return pageResult;
    }

    /**
     * 手动分页
     *
     * @param allList  要分页的列表
     * @param pageNum  页码
     * @param pageSize 页大小
     * @param <T>
     * @return
     */
    public static <T> PageResult<T> startPage(List<T> allList, Integer pageNum, Integer pageSize, Integer total) {
        PageResult<T> pageResult = new PageResult<>();
        final int[] pageArr = PageUtil.transToStartEnd(pageNum - 1, pageSize);
        pageResult.setRecords(CollUtil.sub(allList, pageArr[0], pageArr[1]));
        pageResult.setTotal((long) total);
        pageResult.setSize((long) pageSize);
        pageResult.setCurrent((long) pageNum);
        return pageResult;
    }

}
