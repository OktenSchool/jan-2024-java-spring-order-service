package org.okten.demo.service.impl;

import org.okten.demo.service.InventoryService;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Primary
@Service
public class MockInventoryService implements InventoryService {
    @Override
    public boolean hasEnoughQuantity(Long productId, Integer productCount) {
        return true;
    }
}
