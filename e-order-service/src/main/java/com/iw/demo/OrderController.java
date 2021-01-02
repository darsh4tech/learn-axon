package com.iw.demo;

import java.util.Date;
import java.util.UUID;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iw.demo.domain.model.CreateOrderCommand;

@RestController
@RequestMapping("/orders")
public class OrderController {

	@Autowired
//	@Qualifier("defaultCommandGateway")
	CommandGateway commandGateway; 
//	PaymentClient paymentClient; 
	
	@GetMapping("/order")
	public String makeOrder() {
		try {
			commandGateway.send(CreateOrderCommand.builder().orderId(UUID.randomUUID().toString())
					.creationDate(new Date()).productId("#123456").productName("T-Shirt").price(100).quantity(1).build());
			}catch (Exception e) {
				System.out.println(e);
			}
		return "a new order has placed";
	}
	
}
