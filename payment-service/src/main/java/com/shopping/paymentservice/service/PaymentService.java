package com.shopping.paymentservice.service;

import com.shopping.paymentservice.request.PaymentRequest;
import com.shopping.paymentservice.response.PaymentResponse;

public interface PaymentService {
    
	public long doPayment(PaymentRequest paymentRequest);
    public PaymentResponse getPaymentDetailsByOrderId(long orderId);
}
