package com.cooba.component.wallet;

import com.cooba.enums.WalletEnum;
import com.cooba.exception.InsufficientBalanceException;
import com.cooba.interfaces.ThrowableRunnable;
import com.cooba.object.PlayerWalletResult;
import com.cooba.repository.playerWallet.PlayerWalletRepository;
import com.cooba.util.LockUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@RequiredArgsConstructor
public class SimpleWallet implements Wallet {
    private final PlayerWalletRepository playerWalletRepository;
    private final LockUtil lockUtil;

    @Override
    public PlayerWalletResult increaseAsset(long playerId, int assetId, BigDecimal amount) {
        String key = this.getWalletEnum().name() + playerId + assetId;
        PlayerWalletResult.PlayerWalletResultBuilder walletResultBuilder = PlayerWalletResult.builder();
        try {
            lockUtil.tryLock(key, 3, TimeUnit.SECONDS, () -> {
                Optional<BigDecimal> balance = playerWalletRepository.selectAssetAmount(playerId, assetId);

                if (balance.isEmpty()) {
                    playerWalletRepository.insertAssetAmount(playerId, assetId, amount);
                    walletResultBuilder.amount(amount);
                } else {
                    BigDecimal newBalance = balance.get().add(amount);
                    playerWalletRepository.updateAssetAmount(playerId, assetId, newBalance);
                    walletResultBuilder.amount(newBalance);
                }
            });
            return walletResultBuilder
                    .playerId(playerId)
                    .walletId(getWalletEnum().getId())
                    .assetId(assetId)
                    .build();
        } catch (Exception e) {
            log.error("wallet {} {} {} 新增資產失敗 {}", playerId, assetId, amount, e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public PlayerWalletResult decreaseAsset(long playerId, int assetId, BigDecimal amount) throws InsufficientBalanceException {
        String key = this.getWalletEnum().name() + playerId + assetId;
        PlayerWalletResult.PlayerWalletResultBuilder walletResultBuilder = PlayerWalletResult.builder();
        try {
            lockUtil.tryLock(key, 1, TimeUnit.SECONDS, () -> {
                Optional<BigDecimal> balance = playerWalletRepository.selectAssetAmount(playerId, assetId);

                if (balance.isEmpty()) {
                    throw new InsufficientBalanceException();
                } else {
                    BigDecimal newBalance = balance.get().subtract(amount);

                    if (newBalance.compareTo(BigDecimal.ZERO) < 0) {
                        throw new InsufficientBalanceException();
                    }
                    playerWalletRepository.updateAssetAmount(playerId, assetId, newBalance);
                    walletResultBuilder.amount(newBalance);
                }
            });

        } catch (InsufficientBalanceException e) {
            log.error("wallet {} {} {} {}", playerId, assetId, amount, e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("wallet {} {} {} 減少資產失敗 {}", playerId, assetId, amount, e.getMessage());
            throw new RuntimeException(e);
        }
        return walletResultBuilder
                .playerId(playerId)
                .walletId(getWalletEnum().getId())
                .assetId(assetId)
                .build();
    }

    @Override
    public WalletEnum getWalletEnum() {
        return WalletEnum.SIMPLE;
    }
}
