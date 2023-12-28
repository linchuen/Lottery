package com.cooba.exception;

public class NoLockException extends Exception{
    public NoLockException(){
        super("無法取得鎖");
    }
}
