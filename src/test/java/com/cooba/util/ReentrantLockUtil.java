package com.cooba.util;

import com.cooba.exception.NoLockException;
import com.cooba.others.ThrowableRunnable;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Supplier;

@Component
public class ReentrantLockUtil implements LockUtil {
    private final Map<String, Lock> lockMap = new ConcurrentHashMap<>();

    @Override
    public <T> Optional<T> tryLock(String key, int timeout, TimeUnit timeUnit, int expireTime, Supplier<T> supplier) {
        return Optional.empty();
    }

    @Override
    public void tryLock(String key, int timeout, TimeUnit timeUnit, ThrowableRunnable runnable) throws Exception {
        lockMap.putIfAbsent(key, new ReentrantLock());

        Lock lock = lockMap.get(key);

        try {
            if (lock.tryLock(timeout, timeUnit)) {
                try {
                    runnable.run();
                } finally {
                    lock.unlock();
                }
            } else {
                throw new NoLockException();
            }
        } catch (InterruptedException e) {
            throw new NoLockException();
        }
    }
}
