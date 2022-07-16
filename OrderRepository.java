package com.freshlms.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.freshlms.core.entity.OrderEntity;

public interface OrderRepository extends JpaRepository<OrderEntity, Long>
{

}
