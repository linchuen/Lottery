package com.cooba.interfaces;

@FunctionalInterface
public interface ThrowableRunnable {
    void run() throws Exception;
}
