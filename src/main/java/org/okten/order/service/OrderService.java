package org.okten.order.service;

import lombok.RequiredArgsConstructor;
import org.okten.order.dto.OrderDto;
import org.okten.order.repository.OrderRepository;
import org.okten.order.entity.Order;
import org.okten.order.mapper.OrderMapper;
import org.okten.product.api.ProductApi;
import org.okten.product.api.ProductDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    private final OrderMapper orderMapper;

    private final ProductApi productApi;

    private final InventoryService inventoryService;

    public List<OrderDto> findOrders() {
        return orderRepository
                .findAll()
                .stream()
                .map(orderMapper::mapToDto)
                .toList();
    }

    public OrderDto createOrder(OrderDto orderDto) {
        orderDto.getProductCounts()
                .forEach((productId, productCount) -> {
                    ProductDto product;

                    try {
                        product = productApi.getProduct(productId);
                    } catch (Exception e) {
                        throw new IllegalArgumentException("Product with id '%s' can not be fetched".formatted(productId));
                    }

                    if (ProductDto.AvailabilityEnum.AVAILABLE != product.getAvailability()) {
                        throw new IllegalStateException("Product with id '%s' is not available".formatted(productId));
                    }

                    if (!inventoryService.hasEnoughQuantity(productId, productCount)) {
                        throw new IllegalStateException("There is no '%s' products with id '%s'".formatted(productCount, productId));
                    }
                });
        Order order = orderMapper.mapToEntity(orderDto);
        Order createdOrder = orderRepository.save(order);
        return orderMapper.mapToDto(createdOrder);
    }
}
