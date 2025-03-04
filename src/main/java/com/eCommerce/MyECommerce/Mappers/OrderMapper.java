package com.eCommerce.MyECommerce.Mappers;

import com.eCommerce.MyECommerce.Domain.Entities.OrderEntity;
import com.eCommerce.MyECommerce.Domain.Models.OrderModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.Arrays;

@Mapper(imports={ Arrays.class, String.class }, nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface OrderMapper {

    @Mapping(expression = "java(Arrays.asList(order.getItemIds().split(\",\")))", target = "itemIds")
    OrderModel mapToOrderModel(OrderEntity order);

    @Mapping(expression = "java(String.join(\", \", order.getItemIds()))", target = "itemIds")
    OrderEntity mapToOrderEntity(OrderModel order);
}
