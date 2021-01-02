package com.iw.demo.domain;

import org.axonframework.eventhandling.EventHandler;
import org.axonframework.eventhandling.gateway.EventGateway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.iw.demo.ShippmentService;
import com.iw.demo.domain.model.OrderReadyToBeShipped;
import com.iw.demo.domain.model.OrderShippedWellEvent;

@Component
public class ShippmentEventHandler {
	
	private static final Logger log = LoggerFactory.getLogger(ShippmentEventHandler.class);
	
	@Autowired
	ShippmentService shippmentService;
	
	@Autowired
	EventGateway eventGateway;

	@EventHandler
	public void handleShippment(OrderReadyToBeShipped orderReadyToBeShipped) {
		log.info("orderReadyToBeShipped: {}",orderReadyToBeShipped);
		String ship = shippmentService.ship();
		log.info("shippmentService.ship(): {}",ship);
		
		eventGateway.publish(OrderShippedWellEvent.builder().productId(orderReadyToBeShipped.getProductId())
				.productName(orderReadyToBeShipped.getProductName()).totalPrice(orderReadyToBeShipped.getTotalPrice())
				.orderStatus(orderReadyToBeShipped.getOrderStatus()).orderId(orderReadyToBeShipped.getOrderId()).build());
	}

}
