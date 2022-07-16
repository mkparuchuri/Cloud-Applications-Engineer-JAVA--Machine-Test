package com.freshlms.core.services;

import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.freshlms.core.constants.ErrorConstants;
import com.freshlms.core.entity.ItemEntity;
import com.freshlms.core.entity.OrderEntity;
import com.freshlms.core.exception.DaoException;
import com.freshlms.core.mapper.OrderMapper;
import com.freshlms.core.model.business.OrderItemResponseDto;
import com.freshlms.core.model.controller.context.ApiRequestContext;
import com.freshlms.core.repository.OrderRepository;
import com.freshlms.core.util.ExceptionUtil;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class OrderItemService extends BaseService{

	@Autowired
	private OrderMapper orderMapper;
	
	@Autowired
	private OrderRepository orderRepository;
	
	public ApiRequestContext createOrder(OrderItemResponseDto orderItemResponseDto, ApiRequestContext apiRequestContext)
			throws DaoException {
		log.debug("createOrder method is calling");
		try {
			OrderEntity orderEntity = orderMapper.toEntity(orderItemResponseDto);
			for (ItemEntity itemEntity : orderEntity.getItems()) {
				itemEntity.setOrderEntity(orderEntity);
			}
			orderEntity = orderRepository.save(orderEntity);
			orderEntity.getItems();
			OrderItemResponseDto itemResponseDto = orderMapper.toBusinessModal(orderEntity);
			apiRequestContext.setResponseEntity(itemResponseDto);
		} catch (Exception e) {
			log.error("Error in creating orders: {}, {} ", e.getMessage(), ExceptionUtil.getStackTrace(e));
			apiRequestContext.addError(ErrorConstants.ERROR_SYSTEM);
			throw new DaoException(e, e.getMessage());
		}
		return apiRequestContext;
	}
	
	public ApiRequestContext getOrderByID(Long orderId, ApiRequestContext apiRequestContext) throws DaoException {
		log.debug("Get Order By ID method is calling");
		OrderItemResponseDto orderItemResponseDto = null;
		try {
			Optional<OrderEntity> optionalOrderEntity = orderRepository.findById(orderId);
			if (optionalOrderEntity.isPresent()) {
				OrderEntity orderEntity = optionalOrderEntity.get();
				orderEntity.getItems();
				orderItemResponseDto = orderMapper.toBusinessModal(orderEntity);
				apiRequestContext.setResponseEntity(orderItemResponseDto);
			}
		} catch (Exception e) {
			log.error("Error in get Order By Order ID ", e.getMessage(), ExceptionUtil.getStackTrace(e));
			throw new DaoException(e, e.getMessage());
		}
		return apiRequestContext;
	}
	
	public ApiRequestContext getAllOrders(ApiRequestContext apiRequestContext) {
		log.debug("getAllOrders method is calling");
		try {
			List<OrderEntity> orderEntities = orderRepository.findAll();
			List<OrderItemResponseDto> dtos = orderMapper.toBusinessModal(orderEntities);
			apiRequestContext.setResponseEntity(dtos);
		} catch (Exception e) {
			log.error("Error in getting Orders :{}, {}", e.getMessage(), ExceptionUtil.getStackTrace(e));
			apiRequestContext.addError(ErrorConstants.ERROR_SYSTEM, ExceptionUtils.getRootCauseMessage(e));
		}
		return apiRequestContext;
	}
}
