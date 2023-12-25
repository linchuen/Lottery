package com.cooba.component.wallet;

import com.cooba.enums.WalletEnum;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;

@Getter
@Component
public class WalletFactory {
    private final Map<Integer, Wallet> walletIdMap;
    private final Map<WalletEnum, Wallet> walletMap;

    public WalletFactory(Map<Integer, Wallet> walletIdMap, Map<WalletEnum, Wallet> walletMap) {
        this.walletIdMap = walletIdMap;
        this.walletMap = walletMap;
    }

    public Optional<Wallet> getWallet(int id){
        return Optional.ofNullable(walletIdMap.get(id));
    };
}
