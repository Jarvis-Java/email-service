package com.shopping.orderService.orderservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shopping.orderService.orderservice.beans.Order;

public interface OrderRepository extends JpaRepository<Order,Long> {
}