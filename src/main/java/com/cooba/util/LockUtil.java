package com.cooba.util;

import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

public interface LockUtil {
    <T> T tryLock(String key, int leaseTime, TimeUnit timeUnit, Supplier<T> supplier);

    void tryLock(String key, int leaseTime, TimeUnit timeUnit, Runnable runnable);
}
