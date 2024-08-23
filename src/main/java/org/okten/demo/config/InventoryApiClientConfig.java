package org.okten.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.function.Supplier;

@Configuration
public class InventoryApiClientConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public WebClient inventoryApiWebClient(Supplier<String> currentUserBearerToken) {
        return WebClient.builder()
                .baseUrl("https://66c37744d057009ee9c05e2f.mockapi.io/inventory-service/api/v1")
                .build();
    }
}
