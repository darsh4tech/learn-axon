package com.iw.demo.domain;

import org.axonframework.eventhandling.EventHandler;
import org.axonframework.eventhandling.gateway.EventGateway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.iw.demo.PaymentService;
import com.iw.demo.domain.model.OrderPaidEvent;
import com.iw.demo.domain.model.OrderReadyToBeShipped;
import com.iw.demo.domain.model.OrderStatus;

@Component
public class PaymentEventHandler {

	private static final Logger log = LoggerFactory.getLogger(PaymentEventHandler.class);

	@Autowired
	PaymentService paymentService;

	@Autowired
	EventGateway eventGateway;

	OrderStatus payStatus;
	
	@EventHandler
	public void handlePay(OrderPaidEvent orderPaidEvent) {
		try {
			log.info("orderPaidEvent: {}", orderPaidEvent);
			String pay = paymentService.pay();
			
			if(pay.isEmpty())
				payStatus = OrderStatus.PAYMENT_FAILED;
			else
				payStatus = orderPaidEvent.getOrderStatus();
			
			log.info("paymentService.pay(): {}", paymentService.pay());
			eventGateway.publish(OrderReadyToBeShipped.builder().productId(orderPaidEvent.getProductId())
					.productName(orderPaidEvent.getProductName()).totalPrice(orderPaidEvent.getTotalPrice())
					.orderStatus(payStatus).orderId(orderPaidEvent.getOrderId()).build());

		} catch (Exception e) {
			log.error("Error: {}", e);
		}

	}

}
