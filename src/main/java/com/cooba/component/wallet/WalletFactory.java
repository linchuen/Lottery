package com.cooba.component.wallet;

import com.cooba.enums.WalletEnum;

import java.util.Optional;

public interface WalletFactory {
    Wallet getWallet(WalletEnum walletEnum);

    Optional<Wallet> getWallet(int id);
}
