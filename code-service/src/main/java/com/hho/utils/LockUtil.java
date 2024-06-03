package com.hho.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;


@Slf4j
public class LockUtil {

    // ID对应的lock
    private static final Map<String, ReentrantLock> lockMap = new ConcurrentHashMap<>();

    /**
     * @param key      传要新增或更新的文档的ID
     * @param runnable 逻辑代码执行
     */
    public static void lock(String key, Runnable runnable) {

        // 如果锁的Id为空，直接返回
        if (StringUtils.isNotBlank(key)) {
            ReentrantLock lock = lockMap.computeIfAbsent(key, k -> new ReentrantLock());
            lock.lock();
            try {
                runnable.run();
            } finally {
                lock.unlock();
            }
        } else {
            throw new RuntimeException("加锁失败，ID为空，请确认后再操作");
        }
    }


}
