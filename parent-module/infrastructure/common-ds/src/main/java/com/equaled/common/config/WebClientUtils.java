package com.equaled.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class WebClientUtils {

    @Bean
    public WebClient getWebClient() {
        return (WebClient.builder().build());
    }
}
