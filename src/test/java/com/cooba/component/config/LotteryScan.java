package com.cooba.component.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.ComponentScan;

@TestConfiguration
@ComponentScan("com.cooba.component.Lottery")
public class LotteryScan {
}
