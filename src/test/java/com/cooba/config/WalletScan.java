package com.cooba.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.ComponentScan;

@TestConfiguration
@ComponentScan("com.cooba.component.wallet")
public class WalletScan {
}
