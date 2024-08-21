package org.okten.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductAvailabilityUpdatedEvent {

    private Long productId;

    private ProductAvailability availability;
}
