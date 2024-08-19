package org.okten.order.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.okten.order.dto.OrderDto;
import org.okten.order.entity.Order;

@Mapper
public interface OrderMapper {

    OrderDto mapToDto(Order order);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "productCounts", source = "productCounts")
    Order mapToEntity(OrderDto orderDto);
}
