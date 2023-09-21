package com.shopping.orderService.orderservice.services;

import com.shopping.orderService.orderservice.beans.OrderRequest;
import com.shopping.orderService.orderservice.beans.OrderResponse;

public interface OrderService {
    
	public long placeOrder(OrderRequest orderRequest);
    public OrderResponse getOrderDetails(long orderId);

}