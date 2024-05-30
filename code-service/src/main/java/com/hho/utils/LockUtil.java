package com.hho.utils;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Supplier;


@Slf4j
public class LockUtil {

    private static ReentrantLock reentrantLock = new ReentrantLock();



    public <R> R lockAndDo(String key, Supplier<R> supplier) {
        try {
            boolean islock = reentrantLock.tryLock();
            if (!islock) {
                while (!islock) {
                    islock = reentrantLock.tryLock();
                    log.info(" =============>>> 尝试获取锁  <<<============= " + key);
                    Thread.sleep(300);
                }
            }
            log.info(" =============>>> 主方法体开始执行  <<<============= ");
            return supplier.get();
        } catch (Exception e) {
            log.error(" =============>>> 加锁过程发生异常  <<<============= ", e);
            throw new RuntimeException(e);
        } finally {
            log.info(" =============>>> 解锁  <<<============= ");
            reentrantLock.unlock();
        }
    }

}
