package org.okten.order.service.impl;

import lombok.RequiredArgsConstructor;
import org.okten.order.dto.InventoryDto;
import org.okten.order.service.InventoryService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InventoryServiceWithWebClient implements InventoryService {

    @Qualifier("inventoryApiWebClient")
    private final WebClient webClient;

    @Override
    public boolean hasEnoughQuantity(Long productId, Integer productCount) {
        return webClient
                .get()
                .uri("/inventories")
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<InventoryDto>>() {
                })
                .block()
                .stream()
                .filter(inventoryDto -> inventoryDto.getId().equals(productId.toString()))
                .anyMatch(inventoryDto -> productCount <= inventoryDto.getQuantity());
    }
}
