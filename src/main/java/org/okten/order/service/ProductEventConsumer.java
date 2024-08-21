package org.okten.order.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.okten.demo.dto.ProductAvailability;
import org.okten.demo.dto.ProductAvailabilityUpdatedEvent;
import org.okten.order.entity.OrderStatus;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class ProductEventConsumer implements MessageListener<Integer, ProductAvailabilityUpdatedEvent> {

    private final OrderService orderService;

    @KafkaListener(topics = "${spring.kafka.consumer.topic}", groupId = "order-service-product-listener")
    @Override
    public void onMessage(ConsumerRecord<Integer, ProductAvailabilityUpdatedEvent> record) {
        ProductAvailabilityUpdatedEvent event = record.value();
        log.info("Received product availability updated event: {}", event);

        if (event.getAvailability() == ProductAvailability.AVAILABLE) {
            orderService.updateOrdersStatusWithProduct(event.getProductId());
        }
    }
}