package com.cooba.component.wallet;

import com.cooba.enums.WalletEnum;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
@Component
public class WalletFactoryImpl implements WalletFactory{
    private final Map<Integer, Wallet> walletIdMap;
    private final Map<WalletEnum, Wallet> walletMap;

    public WalletFactoryImpl(List<Wallet> wallets) {
        this.walletIdMap = wallets.stream().collect(Collectors.toMap(wallet -> wallet.getWalletEnum().getId(), Function.identity()));
        this.walletMap = wallets.stream().collect(Collectors.toMap(Wallet::getWalletEnum, Function.identity()));
    }

    @Override
    public Wallet getWallet(WalletEnum walletEnum) {
        return walletMap.get(walletEnum);
    }

    public Optional<Wallet> getWallet(int id) {
        return Optional.ofNullable(walletIdMap.get(id));
    }
}
