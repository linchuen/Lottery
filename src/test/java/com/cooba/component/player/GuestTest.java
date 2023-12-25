package com.cooba.component.player;

import com.cooba.component.order.Order;
import com.cooba.component.wallet.Wallet;
import com.cooba.component.wallet.WalletFactory;
import com.cooba.repository.FakeOrderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {Guest.class, FakeOrderRepository.class})
class GuestTest {
    @Autowired
    Guest guest;
    @MockBean
    Order order;
    @MockBean
    WalletFactory walletFactory;
    @MockBean
    Wallet wallet;

    @Test
    public void bet() {
    }
}