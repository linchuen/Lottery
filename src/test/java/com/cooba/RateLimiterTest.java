package com.cooba;

import com.google.common.util.concurrent.RateLimiter;

import java.util.Date;

public class RateLimiterTest {

    public static void main(String[] args) {
        RateLimiter limiter = RateLimiter.create(1.0); // 这里的1表示每秒允许处理的量为1个
        for (int i = 1; i <= 10; i++) {
            limiter.acquire();// 请求RateLimiter, 超过permits会被阻塞
            System.out.println("call execute.." + i + new Date());
        }
    }
}
