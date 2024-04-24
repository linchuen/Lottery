package com.cooba.util;

import com.cooba.exception.NoLockException;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

@Component
@RequiredArgsConstructor
public class RedissonLockUtil implements LockUtil {
    private final RedissonClient redissonClient;

    @Override
    public <T> T tryLock(String key, int leaseTime, TimeUnit timeUnit, Supplier<T> supplier) {
        RLock lock = redissonClient.getLock(key);

        long waitTime = timeUnit.toMillis(leaseTime);
        try {
            if (lock.tryLock(waitTime, leaseTime, timeUnit)) {
                try {
                   return supplier.get();
                } finally {
                    lock.unlock();
                }
            }
        } catch (InterruptedException e) {
            throw new NoLockException();
        }
        throw new NoLockException();
    }

    @Override
    public void tryLock(String key, int leaseTime, TimeUnit timeUnit, Runnable runnable) {
        RLock lock = redissonClient.getLock(key);

        long waitTime = timeUnit.toMillis(leaseTime);
        try {
            if (lock.tryLock(waitTime, leaseTime, timeUnit)) {
                try {
                    runnable.run();
                } finally {
                    lock.unlock();
                }
            }
        } catch (InterruptedException e) {
            throw new NoLockException();
        }
        throw new NoLockException();
    }
}
