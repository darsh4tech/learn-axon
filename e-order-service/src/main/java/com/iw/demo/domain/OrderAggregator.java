package com.iw.demo.domain;

import java.util.Date;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.iw.demo.domain.model.CreateOrderCommand;
import com.iw.demo.domain.model.OrderCreatedEvent;
import com.iw.demo.domain.model.OrderPaidEvent;
import com.iw.demo.domain.model.OrderStatus;
import com.iw.demo.domain.model.OrderUpdatedCommand;
import com.iw.demo.domain.model.OrderUpdatedEvent;
import com.iw.demo.domain.model.PayOrderCommand;

import lombok.Getter;
import lombok.Setter;

//@Component
@Getter
@Setter
@Aggregate
public class OrderAggregator {

	private static final Logger log = LoggerFactory.getLogger(OrderAggregator.class);

	@AggregateIdentifier
	private String orderId;
	private String productId;
	private String productName;
	private Integer price;
	private Integer quantity;
	private Integer totoalPrice;
	private Date creationDate;
	private OrderStatus orderStatus;
	
	// first command will get handled will get placed in constructor
	@CommandHandler
	public OrderAggregator(CreateOrderCommand createOrderCommand) {
		try {
			if (createOrderCommand.getProductId().isEmpty())
				throw new IllegalArgumentException("Product Id is empty");
			
			if (createOrderCommand.getProductName().isEmpty())
				throw new IllegalArgumentException("Product Name is empty");
			
			OrderCreatedEvent orderCreatedEvent = OrderCreatedEvent.builder().orderId(createOrderCommand.getOrderId())
					.productId(createOrderCommand.getProductId()).productName(createOrderCommand.getProductName())
					.price(createOrderCommand.getPrice()).quantity(createOrderCommand.getQuantity())
					.creationDate(createOrderCommand.getCreationDate()).orderStatus(OrderStatus.NEW)
					.makeOrderResult("not called").build();
			
			log.info("OrderCreatedEvent Fired : {}",orderCreatedEvent);
			AggregateLifecycle.apply(orderCreatedEvent);
		}catch (Exception e) {
			log.error("Error: {}",e);
		}
	}

	@EventSourcingHandler()
	public void on(OrderCreatedEvent orderCreatedEvent) {
		try {
			this.orderId = orderCreatedEvent.getOrderId();//this aggregate id must be set 
			this.productId = orderCreatedEvent.getProductId();
			this.productName = orderCreatedEvent.getProductName();
			this.price = orderCreatedEvent.getPrice();
			this.quantity = orderCreatedEvent.getQuantity();
			this.creationDate = orderCreatedEvent.getCreationDate();
			this.totoalPrice = this.price * this.quantity; 
			this.orderStatus = orderCreatedEvent.getOrderStatus();
			
			log.info("calling >>>>> on(OrderCreatedEvent)");
			
		}catch (Exception e) {
			log.error("Error: {}",e);
		}
	}

	@CommandHandler
	public void handleCmd(PayOrderCommand payOrderCommand) {

		try {
			
			if(this.orderStatus.equals(OrderStatus.PAID))
				throw new IllegalArgumentException("this Order is PAID");
			
			if (this.totoalPrice == 0)
				throw new IllegalArgumentException("total price is 0 So, No Price Provided Or Quantity Not Provided");
			
			OrderPaidEvent orderPaidEvent = OrderPaidEvent.builder().orderId(payOrderCommand.getOrderId())
					.productId(payOrderCommand.getProductId()).productName(payOrderCommand.getProductName())
					.totalPrice(this.totoalPrice).creationDate(payOrderCommand.getCreationDate())
					.orderStatus(payOrderCommand.getOrderStatus())
					.paymentStr("not called").build();
			
			log.info("orderPaidEvent Fired : {}",orderPaidEvent);
			AggregateLifecycle.apply(orderPaidEvent);
		} catch (Exception e) {
			log.error("Error: {}",e);
		}
	}
	
	@EventSourcingHandler
	public void on(OrderPaidEvent orderPaidEvent) {
		this.orderStatus = OrderStatus.PAID;
		orderPaidEvent.setOrderStatus(this.orderStatus);
		log.info("calling >>>>> on(OrderPaidEvent)");
	}
	
	@CommandHandler
	public void handleCmd(OrderUpdatedCommand orderUpdatedCommand) {

		try {
			
			if(this.orderStatus.equals(OrderStatus.PAYMENT_FAILED))
				throw new IllegalArgumentException("this Order is PAYMENT FAILED");
			
			OrderUpdatedEvent orderUpdatedEvent = OrderUpdatedEvent.builder().orderId(orderUpdatedCommand.getOrderId())
					.productId(orderUpdatedCommand.getProductId()).productName(orderUpdatedCommand.getProductName())
					.creationDate(orderUpdatedCommand.getCreationDate())
					.orderStatus(orderUpdatedCommand.getOrderStatus()).build();
			
			log.info("orderPaidEvent Fired : {}",orderUpdatedEvent);
			AggregateLifecycle.apply(orderUpdatedEvent);
		} catch (Exception e) {
			log.error("Error: {}",e);
		}
	}
	
	@EventSourcingHandler
	public void on(OrderUpdatedEvent orderUpdatedEvent) {
		this.orderStatus = OrderStatus.SHIPPED_WELL;
		orderUpdatedEvent.setOrderStatus(this.orderStatus);
		log.info("calling >>>>> on(orderUpdatedEvent): {}",orderUpdatedEvent);
	}
	
	protected OrderAggregator() {
		log.info("Empty Order Created");
	}
}
