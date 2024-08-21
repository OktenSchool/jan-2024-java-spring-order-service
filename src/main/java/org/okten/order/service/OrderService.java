package org.okten.order.service;

import lombok.RequiredArgsConstructor;
import org.okten.order.dto.OrderDto;
import org.okten.order.entity.OrderStatus;
import org.okten.order.repository.OrderRepository;
import org.okten.order.entity.Order;
import org.okten.order.mapper.OrderMapper;
import org.okten.product.api.ProductApi;
import org.okten.product.api.ProductDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

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

                    if (ProductDto.AvailabilityEnum.DISCONTINUED == product.getAvailability()) {
                        throw new IllegalStateException("Product with id '%s' is discontinued".formatted(productId));
                    }

                    if (!inventoryService.hasEnoughQuantity(productId, productCount)) {
                        throw new IllegalStateException("There is no '%s' products with id '%s'".formatted(productCount, productId));
                    }
                });
        Order order = orderMapper.mapToEntity(orderDto);
        order.setStatus(OrderStatus.DRAFT);
        Order createdOrder = orderRepository.save(order);
        return orderMapper.mapToDto(createdOrder);
    }

    @Transactional
    public void updateOrdersStatusWithProduct(Long productId) {
        orderRepository
                .findAll()
                .stream()
                .filter(order -> order.getProductCounts().containsKey(productId))
                .filter(order -> order
                        .getProductCounts()
                        .entrySet()
                        .stream()
                        .allMatch((entry) -> {
                            if (!Objects.equals(entry.getKey(), productId)) {
                                ProductDto product;

                                try {
                                    product = productApi.getProduct(entry.getKey());
                                } catch (Exception e) {
                                    throw new IllegalArgumentException("Product with id '%s' can not be fetched".formatted(entry.getKey()));
                                }

                                return ProductDto.AvailabilityEnum.AVAILABLE == product.getAvailability();
                            } else {
                                return true;
                            }
                        }))
                .forEach(order -> {
                    order.setStatus(OrderStatus.IN_PROGRESS);
                    orderRepository.save(order);
                });
    }
}
