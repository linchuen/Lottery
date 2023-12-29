package com.cooba.util;

import com.cooba.exception.NoLockException;
import com.cooba.interfaces.ThrowableRunnable;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.function.Supplier;

@Component
@RequiredArgsConstructor
public class RedissonLockUtil implements LockUtil {
    private final RedissonClient redissonClient;

    @Override
    public <T> Optional<T> tryLock(String key, int timeout, TimeUnit timeUnit, int expireTime, Supplier<T> supplier) {
        RLock lock = redissonClient.getLock(key);
        try {
            if (lock.tryLock(timeout, expireTime, timeUnit)) {
                return Optional.ofNullable(supplier.get());
            }
        } catch (InterruptedException e) {
            return Optional.empty();
        }
        return Optional.empty();
    }

    @Override
    public void tryLock(String key, int timeout, TimeUnit timeUnit, ThrowableRunnable runnable) throws Exception {
        Lock lock = redissonClient.getLock(key);

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
