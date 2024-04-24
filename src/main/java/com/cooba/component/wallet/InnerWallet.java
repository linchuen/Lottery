package com.cooba.component.wallet;

import com.cooba.enums.WalletEnum;
import com.cooba.exception.InsufficientBalanceException;
import com.cooba.object.PlayerWalletResult;
import com.cooba.repository.playerWallet.PlayerWalletRepository;
import com.cooba.util.LockUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@RequiredArgsConstructor
public class InnerWallet implements Wallet {
    private final PlayerWalletRepository playerWalletRepository;
    private final LockUtil lockUtil;

    @Override
    public PlayerWalletResult increaseAsset(long playerId, int assetId, BigDecimal amount) {
        String key = this.getWalletEnum().name() + playerId + assetId;

        BigDecimal updatedBalance = lockUtil.tryLock(key, 3, TimeUnit.SECONDS, () -> {
            BigDecimal balance = playerWalletRepository.selectAssetAmount(playerId, assetId).orElse(BigDecimal.ZERO);
            BigDecimal newBalance = balance.add(amount);
            playerWalletRepository.updateAssetAmount(playerId, assetId, newBalance);
            return newBalance;
        });

        return PlayerWalletResult.builder()
                .amount(updatedBalance)
                .playerId(playerId)
                .walletId(getWalletEnum().getId())
                .assetId(assetId)
                .build();
    }

    @Override
    public PlayerWalletResult decreaseAsset(long playerId, int assetId, BigDecimal amount) {
        String key = this.getWalletEnum().name() + playerId + assetId;
        PlayerWalletResult.PlayerWalletResultBuilder walletResultBuilder = PlayerWalletResult.builder();

        BigDecimal updatedBalance = lockUtil.tryLock(key, 1, TimeUnit.SECONDS, () -> {
            BigDecimal balance = playerWalletRepository.selectAssetAmount(playerId, assetId)
                    .orElseThrow(InsufficientBalanceException::new);

            BigDecimal newBalance = balance.subtract(amount);
            if (newBalance.compareTo(BigDecimal.ZERO) < 0) {
                throw new InsufficientBalanceException();
            }
            playerWalletRepository.updateAssetAmount(playerId, assetId, newBalance);
            return newBalance;
        });

        return PlayerWalletResult.builder()
                .amount(updatedBalance)
                .playerId(playerId)
                .walletId(getWalletEnum().getId())
                .assetId(assetId)
                .build();
    }

    @Override
    public WalletEnum getWalletEnum() {
        return WalletEnum.INNER;
    }
}
