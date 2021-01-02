package com.iw.demo.domain.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.iw.demo.domain.projection.OrderSummaryEntity;

@Repository
public interface OrderSummaryEntityRepository extends JpaRepository<OrderSummaryEntity, String>{

	List<OrderSummaryEntity> findByProductId(String productId);
	
}
