package com.iw.demo.domain.projection;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.iw.demo.domain.model.OrderStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "order_summary",indexes = {@Index(columnList = "productId")})
@NamedQueries({ //Named Queries
    @NamedQuery(name = "OrderSummaryEntity.findByProductId",
            query = "Select o from OrderSummaryEntity o where o.productId = :productId") })
public class OrderSummaryEntity implements Serializable{

	private static final long serialVersionUID = 4733132395494753275L;
	
	@Id
	private String orderId;
	private String productId;
	private String productName;
	private Integer price;
	private Integer quantity;
	@Temporal(TemporalType.TIMESTAMP) 
	private Date creationDate;
	@Enumerated(EnumType.STRING)
	private OrderStatus orderStatus;
	
}
