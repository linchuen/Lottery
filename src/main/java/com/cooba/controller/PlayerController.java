package com.cooba.controller;

import com.cooba.component.player.Player;
import com.cooba.exception.InsufficientBalanceException;
import com.cooba.object.BetResult;
import com.cooba.object.CreatePlayerResult;
import com.cooba.object.PlayerWalletResult;
import com.cooba.request.BetRequest;
import com.cooba.request.CreatePlayerRequest;
import com.cooba.request.WalletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("player")
@RequiredArgsConstructor
public class PlayerController {
    private final Player player;

    @PostMapping("create")
    public ResponseEntity<CreatePlayerResult> create(@RequestBody CreatePlayerRequest createRequest) {
        CreatePlayerResult createPlayerResult = player.create(createRequest);
        return ResponseEntity.ok(createPlayerResult);
    }

    @PostMapping("bet")
    public ResponseEntity<BetResult> bet(@RequestBody BetRequest betRequest) {
        BetResult betResult = player.bet(betRequest);
        return ResponseEntity.ok(betResult);
    }

    @PostMapping("deposit/{playerId}")
    public ResponseEntity<PlayerWalletResult> deposit(@PathVariable long playerId, @RequestBody WalletRequest walletRequest) {
        PlayerWalletResult walletResult = player.deposit(playerId, walletRequest);
        return ResponseEntity.ok(walletResult);
    }

    @PostMapping("withdraw/{playerId}")
    public ResponseEntity<PlayerWalletResult> withdraw(@PathVariable long playerId, @RequestBody WalletRequest walletRequest) throws InsufficientBalanceException {
        PlayerWalletResult walletResult = player.withdraw(playerId, walletRequest);
        return ResponseEntity.ok(walletResult);
    }
}
