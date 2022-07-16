package com.freshlms.core.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.freshlms.core.constants.ErrorConstants;
import com.freshlms.core.constants.UrlConstants;
import com.freshlms.core.model.business.OrderItemResponseDto;
import com.freshlms.core.model.controller.context.ApiRequestContext;
import com.freshlms.core.services.OrderItemService;
import com.freshlms.core.util.ExceptionUtil;

import lombok.extern.slf4j.Slf4j;

@RestController
@CrossOrigin
@RequestMapping(value = UrlConstants.BASE_URL + "/order")
@Slf4j
public class OrderItemController extends BaseController{

	@Autowired
	private OrderItemService orderItemService;
	
	@Autowired
	private HttpServletRequest servletRequest;
	
	@PostMapping
	public ResponseEntity<Object> createOrder(@RequestBody OrderItemResponseDto orderItemResponseDto) {
		log.debug("createOrder method is calling");
		ApiRequestContext apiRequestContext = getApiRequestContext(servletRequest);
		try {
			orderItemService.createOrder(orderItemResponseDto, apiRequestContext);
		} catch (Exception e) {
			apiRequestContext.addError(ErrorConstants.ERROR_SYSTEM);
			log.error("Error in creating Order :{}, {} ", e.getMessage(), ExceptionUtil.getStackTrace(e));
		}
		return buildResponse(apiRequestContext);
	}
	
	@GetMapping(value = "/{orderId}")
	public ResponseEntity<Object> getOrderByID(@PathVariable("orderId") Long orderId) {
		log.debug("getDigitalDownloadByID is calling " + orderId);
		ApiRequestContext apiRequestContext = null;
		try {
			apiRequestContext = getApiRequestContext(servletRequest);
			apiRequestContext = orderItemService.getOrderByID(orderId, apiRequestContext);
		} catch (Exception e) {
			apiRequestContext.addError(ErrorConstants.ERROR_SYSTEM);
			log.error("Error in getting Order By Id :{}, {} ", e.getMessage(), ExceptionUtil.getStackTrace(e));
		}
		return buildResponse(apiRequestContext);
	}
	
	@GetMapping
	public ResponseEntity<Object> getAllOrders() {
		log.debug("getAllOrders Method is calling");
		ApiRequestContext apiRequestContext = null;
		try {
			apiRequestContext = getApiRequestContext(servletRequest);
			apiRequestContext = orderItemService.getAllOrders(apiRequestContext);
		} catch (Exception e) {
			apiRequestContext.addError(ErrorConstants.ERROR_SYSTEM);
			log.error("Error in getting Orders :{}, {} ", e.getMessage(), ExceptionUtil.getStackTrace(e));
		}
		return buildResponse(apiRequestContext);
	}
}
