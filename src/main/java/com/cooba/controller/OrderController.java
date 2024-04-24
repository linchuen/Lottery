package com.cooba.controller;

import com.cooba.component.order.Order;
import com.cooba.component.player.Player;
import com.cooba.exception.InsufficientBalanceException;
import com.cooba.object.BetResult;
import com.cooba.object.CreatePlayerResult;
import com.cooba.object.PlayerWalletResult;
import com.cooba.request.BetRequest;
import com.cooba.request.CreatePlayerRequest;
import com.cooba.request.WalletRequest;
import com.google.common.util.concurrent.RateLimiter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("order")
@RequiredArgsConstructor
public class OrderController {
    private final Order order;

    @GetMapping("orderNo")
    public ResponseEntity<String> generateOrderNo() {
        String orderNo = order.generateOrderNo();
        return ResponseEntity.ok(orderNo);
    }
}
