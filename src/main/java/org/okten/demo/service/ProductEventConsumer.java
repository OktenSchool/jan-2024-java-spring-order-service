package org.okten.demo.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.okten.demo.api.event.dto.ProductAvailabilityUpdatedPayload;
import org.okten.demo.api.event.producer.IProductAvailabilityUpdatedConsumerService;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class ProductEventConsumer implements IProductAvailabilityUpdatedConsumerService {

    private final OrderService orderService;

    @Override
    public void productAvailabilityUpdated(ProductAvailabilityUpdatedPayload payload, ProductAvailabilityUpdatedPayloadHeaders headers) {
        if (payload.getAvailability() == ProductAvailabilityUpdatedPayload.Availability.AVAILABLE) {
            orderService.updateOrdersStatusWithProduct(payload.getProductId().longValue());
        }
    }
}