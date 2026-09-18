package com.pkfrc.devises.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Value("${exchange.api.base-url}")
    private String baseUrl;

    @Bean
    public WebClient exchangeRateWebClient() {
        return WebClient.builder()
                .baseUrl(baseUrl)
                .build();
    }
}
