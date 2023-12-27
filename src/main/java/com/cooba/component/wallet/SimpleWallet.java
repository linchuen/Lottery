package com.cooba.component.wallet;

import com.cooba.enums.WalletEnum;
import com.cooba.exception.InsufficientBalanceException;
import com.cooba.repository.PlayerWalletRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class SimpleWallet implements Wallet {
    private final PlayerWalletRepository playerWalletRepository;

    @Override
    public void depositAsset(long playerId, int assetId, BigDecimal amount) {
        Optional<BigDecimal> balance = playerWalletRepository.selectAssetAmount(playerId, assetId);

        if (balance.isEmpty()) {
            playerWalletRepository.insertAssetAmount(playerId, assetId, amount);
        } else {
            BigDecimal newBalance = balance.get().add(amount);
            playerWalletRepository.updateAssetAmount(playerId, assetId, newBalance);
        }
    }

    @Override
    public void withdrawAsset(long playerId, int assetId, BigDecimal amount) throws InsufficientBalanceException {
        Optional<BigDecimal> balance = playerWalletRepository.selectAssetAmount(playerId, assetId);

        if (balance.isEmpty()) {
            playerWalletRepository.insertAssetAmount(playerId, assetId, BigDecimal.ZERO);
        } else {
            BigDecimal newBalance = balance.get().subtract(amount);

            if (newBalance.compareTo(BigDecimal.ZERO) < 0) {
                log.error("玩家: {} 餘額不足",playerId);
                throw new InsufficientBalanceException();
            }
            playerWalletRepository.updateAssetAmount(playerId, assetId, newBalance);
        }
    }

    @Override
    public WalletEnum getWalletEnum() {
        return WalletEnum.SIMPLE;
    }
}
