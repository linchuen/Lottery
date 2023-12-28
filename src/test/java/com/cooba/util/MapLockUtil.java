package com.cooba.util;

import com.cooba.others.ThrowableRunnable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;

@Slf4j
@Component
public class MapLockUtil implements LockUtil {
    private final Map<String, Long> lockMap = new ConcurrentHashMap<>();

    @Override
    public <T> Optional<T> tryLock(String key, int timeout, TimeUnit timeUnit, int expireTime, Supplier<T> supplier) {
        AtomicReference<Boolean> lock = new AtomicReference<>();
        lock.set(false);
        lockMap.computeIfAbsent(key, k -> {
            long releaseTime = System.currentTimeMillis() + expireTime * 1000L;
            lock.set(true);
            return releaseTime;
        });

        boolean isGet = lock.get();
        if (isGet) {
            return Optional.of(supplier.get());
        }

        Long releaseTime = lockMap.get(key);
        if (releaseTime > System.currentTimeMillis()) return Optional.empty();
        lockMap.put(key, System.currentTimeMillis() + expireTime * 1000L);
        return Optional.of(supplier.get());
    }

    @Override
    public void tryLock(String key, int timeout, TimeUnit timeUnit, ThrowableRunnable runnable) throws Exception {

    }
}
