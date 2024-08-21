package org.okten.order.config;

import org.okten.product.ApiClient;
import org.okten.product.api.ProductApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Supplier;

@Configuration
public class ProductApiClientConfig {

    @Bean
    public ApiClient apiClient(Supplier<String> currentUserBearerToken) {
        ApiClient apiClient = new ApiClient();
        apiClient.setBasePath("http://localhost:8080");
//        apiClient.setBearerToken(currentUserBearerToken);
        return apiClient;
    }

    @Bean
    public ProductApi productApi(ApiClient apiClient) {
        return new ProductApi(apiClient);
    }
}
