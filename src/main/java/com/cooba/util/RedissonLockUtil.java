package com.cooba.util;

import com.cooba.others.ThrowableRunnable;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

@Component
public class RedissonLockUtil implements LockUtil{
    @Override
    public <T> Optional<T> tryLock(String key, int timeout, TimeUnit timeUnit, int expireTime, Supplier<T> supplier) {
        return Optional.empty();
    }

    @Override
    public void tryLock(String key, int timeout, TimeUnit timeUnit, ThrowableRunnable runnable) throws Exception {

    }
}
