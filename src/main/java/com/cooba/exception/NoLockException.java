package com.cooba.exception;

public class NoLockException extends RuntimeException{
    public NoLockException(){
        super("無法取得鎖");
    }
}
