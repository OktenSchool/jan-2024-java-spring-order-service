package org.okten.demo.service;

public interface InventoryService {

    boolean hasEnoughQuantity(Long productId, Integer productCount);
}
