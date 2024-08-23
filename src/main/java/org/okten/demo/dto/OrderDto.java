package org.okten.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.okten.demo.entity.OrderStatus;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDto {

    private ObjectId id;

    private LocalDateTime timestamp;

    private String user;

    private Map<Long, Integer> productCounts;

    private OrderStatus status;
}
