package com.cooba.util;

import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

public interface LockUtil {
    <T> Optional<T> tryLock(String key, int timeout, TimeUnit timeUnit, int expireTime, Supplier<T> supplier);
}
