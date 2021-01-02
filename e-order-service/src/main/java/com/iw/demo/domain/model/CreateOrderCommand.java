package com.iw.demo.domain.model;

import java.util.Date;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CreateOrderCommand {

	@TargetAggregateIdentifier
	private String orderId;
	private String productId;
	private String productName;
	private Integer price;
	private Integer quantity;
	private Date creationDate;
//	private OrderStatus orderStatus;
	
}
