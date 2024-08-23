package org.okten.demo.service.impl;

import lombok.RequiredArgsConstructor;
import org.okten.demo.dto.InventoryDto;
import org.okten.demo.service.InventoryService;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InventoryServiceWithRestTemplate implements InventoryService {

    private final RestTemplate restTemplate;

    @Override
    public boolean hasEnoughQuantity(Long productId, Integer productCount) {
        ResponseEntity<List<InventoryDto>> response = restTemplate.exchange(
                "https://66c37744d057009ee9c05e2f.mockapi.io/inventory-service/api/v1/inventories",
                HttpMethod.GET,
                new HttpEntity<>(Collections.emptyMap()),
                new ParameterizedTypeReference<>() {
                });

        List<InventoryDto> inventories = response.getBody();

        return inventories
                .stream()
                .filter(inventoryDto -> inventoryDto.getId().equals(productId.toString()))
                .anyMatch(inventoryDto -> productCount <= inventoryDto.getQuantity());
    }
}
