package org.okten.demo.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.okten.demo.dto.OrderDto;
import org.okten.demo.entity.Order;

@Mapper
public interface OrderMapper {

    OrderDto mapToDto(Order order);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "productCounts", source = "productCounts")
    Order mapToEntity(OrderDto orderDto);
}
