package com.cooba.config;

import lombok.Getter;
import lombok.Setter;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "spring.redis")
public class RedissonConfig {
    private String host;
    private int port;

    @Bean
    @ConfigurationProperties(prefix = "spring.redis")
    public RedissonClient redissonClient() {
        //Config.fromYAML(ResourceUtils.getFile("classpath:redisson.yml"));
        Config config = new Config();
        config.useSingleServer()
                .setAddress(String.format("redis://%s:%d", host, port));
        return Redisson.create(config);
    }
}
