package com.hho.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;


@Slf4j
public class LockUtil {

    private static final Map<String, ReentrantLock> lockMap = new ConcurrentHashMap<>();

    /**
     *
     * @param key 锁的ID
     * @param runnable 执行语句
     */
    public static void lock(String key, Runnable runnable) {
        // 如果锁的Id为空，直接返回
        if (StringUtils.isBlank(key)) {
            ReentrantLock lock = lockMap.computeIfAbsent(key, k -> new ReentrantLock());
            lock.lock();
            try {
                runnable.run();
            } finally {
                lock.unlock();
            }
        }
    }

}
