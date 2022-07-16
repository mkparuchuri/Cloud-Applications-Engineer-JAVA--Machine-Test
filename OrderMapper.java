package com.freshlms.core.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.freshlms.core.entity.OrderEntity;
import com.freshlms.core.model.business.OrderItemResponseDto;

@Mapper(componentModel = "spring", uses = {})
public interface OrderMapper extends EntityMapper<OrderItemResponseDto, OrderEntity>{

	OrderItemResponseDto toBusinessModal(OrderEntity orderEntity);

	OrderEntity toEntity(OrderItemResponseDto orderItemResponseDto);

	List<OrderItemResponseDto> map(List<OrderEntity> orderEntities);
	
	default OrderEntity fromId(Long id) {

		if (id == null) {
			return null;
		}
		OrderEntity orderEntity = new OrderEntity();
		orderEntity.setId(id);
		return orderEntity;
	}
}
