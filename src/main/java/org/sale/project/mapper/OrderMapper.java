package org.sale.project.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.sale.project.entity.CartItem;
import org.sale.project.entity.Order;
import org.sale.project.entity.OrderDetail;

import java.lang.annotation.Target;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    @Mapping(target = "price", ignore = true)
    void updateOrder(@MappingTarget OrderDetail detail, CartItem cartItem);
}
