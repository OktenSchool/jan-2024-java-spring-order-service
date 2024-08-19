package org.okten.order.service;

public interface InventoryService {

    boolean hasEnoughQuantity(Long productId, Integer productCount);
}
