package com.iw.demo.domain;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.modelling.saga.EndSaga;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.spring.stereotype.Saga;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.iw.demo.domain.model.OrderCreatedEvent;
import com.iw.demo.domain.model.OrderShippedWellEvent;
import com.iw.demo.domain.model.OrderUpdatedCommand;
import com.iw.demo.domain.model.PayOrderCommand;
import com.iw.demo.domain.projection.OrderSummaryEntity;
import com.iw.demo.domain.repo.OrderSummaryEntityRepository;

@Saga
public class OrderSagaManager {

	private static final Logger log = LoggerFactory.getLogger(OrderSagaManager.class);

	@Autowired
	OrderSummaryEntityRepository entityRepository; 
	
	@Autowired
	private CommandGateway commandGateway;

	@StartSaga
	@SagaEventHandler(associationProperty = "orderId")
	public void handleCreate(OrderCreatedEvent orderCreatedEvent) {
		try {
			log.info("handleCreate >>>> orderId: {} , productId: {}",orderCreatedEvent.getOrderId(),
					orderCreatedEvent.getProductId());
			
			OrderSummaryEntity orderSummaryEntity = OrderSummaryEntity.builder().orderId(orderCreatedEvent.getOrderId())
					.productId(orderCreatedEvent.getProductId()).productName(orderCreatedEvent.getProductName())
					.price(orderCreatedEvent.getPrice()).orderStatus(orderCreatedEvent.getOrderStatus())
					.creationDate(orderCreatedEvent.getCreationDate()).quantity(orderCreatedEvent.getQuantity())
					.build();
			
			entityRepository.save(orderSummaryEntity);
			
			commandGateway.send(PayOrderCommand.builder().orderId(orderCreatedEvent.getOrderId())
					.productId(orderCreatedEvent.getProductId()).productName(orderCreatedEvent.getProductName())
					.orderStatus(orderCreatedEvent.getOrderStatus()).build());			
		} catch (Exception e) {
			log.error("Error {}",e);
		}

	}

	@EndSaga 
	@SagaEventHandler(associationProperty = "orderId")
	public void hnaldeShippment(OrderShippedWellEvent orderShippedWellEvent) {
		log.info("hnaldeShippment >>>> {}",orderShippedWellEvent);
		commandGateway.send(OrderUpdatedCommand.builder().orderId(orderShippedWellEvent.getOrderId())
				.productId(orderShippedWellEvent.getProductId()).productName(orderShippedWellEvent.getProductName())
				.orderStatus(orderShippedWellEvent.getOrderStatus()).build());

	}
}
