package com.iw.demo.domain.model;

import java.util.Date;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class OrderCreatedEvent {

	private String orderId;
	private String productId;
	private String productName;
	private Integer price;
	private Integer quantity;
	private Date creationDate;
	private OrderStatus orderStatus;
	private String makeOrderResult;
	
}
