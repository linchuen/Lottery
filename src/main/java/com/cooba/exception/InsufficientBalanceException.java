package com.cooba.exception;

public class InsufficientBalanceException extends Exception {
    public InsufficientBalanceException(){
        super("餘額不足");
    }

    public InsufficientBalanceException(String msg) {
        super(msg);
    }
}
