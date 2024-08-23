package org.okten.demo.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.okten.demo.api.event.dto.ProductAvailabilityUpdatedPayload;
import org.okten.demo.api.event.producer.IProductAvailabilityUpdatedConsumerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Primary;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

//@Primary
//@Component("product-availability-updated")
@Slf4j
@RequiredArgsConstructor
public class ProductAvailabilityUpdatedConsumer implements Consumer<Message<ProductAvailabilityUpdatedPayload>> {

    private final OrderService orderService;

    @Override
    public void accept(Message<ProductAvailabilityUpdatedPayload> message) {
        log.debug("Received message: {}", message);
        ProductAvailabilityUpdatedPayload payload = message.getPayload();

        if (payload.getAvailability() == ProductAvailabilityUpdatedPayload.Availability.AVAILABLE) {
            orderService.updateOrdersStatusWithProduct(payload.getProductId().longValue());
        }
    }
}
