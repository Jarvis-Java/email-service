package com.shopping.orderService.orderservice.services;

import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.shopping.orderService.orderservice.beans.Order;
import com.shopping.orderService.orderservice.beans.OrderRequest;
import com.shopping.orderService.orderservice.beans.OrderResponse;
import com.shopping.orderService.orderservice.beans.PaymentResponse;
import com.shopping.orderService.orderservice.beans.ProductResponse;
import com.shopping.orderService.orderservice.exception.OrderServiceCustomException;
import com.shopping.orderService.orderservice.feignClient.EmailClient;
import com.shopping.orderService.orderservice.feignClient.SMSClient;
import com.shopping.orderService.orderservice.repository.OrderRepository;
import com.shopping.orderService.orderservice.request.EmailRequest;
import com.shopping.orderService.orderservice.request.SmsRequest;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    private final RestTemplate restTemplate;
    
    @Autowired
    private SMSClient smsClient;
    
    @Autowired
    private EmailClient emailClient;

    @Override
    public long placeOrder(OrderRequest orderRequest) {

        log.info("OrderServiceImpl | placeOrder is called");

        //Order Entity -> Save the data with Status Order Created
        //Product Service - Block Products (Reduce the Quantity)
        //Payment Service -> Payments -> Success-> COMPLETE, Else
        //CANCELLED

        log.info("OrderServiceImpl | placeOrder | Placing Order Request orderRequest : " + orderRequest.toString());

        log.info("OrderServiceImpl | placeOrder | Creating Order with Status CREATED");
        Order order = Order.builder()
                .amount(orderRequest.getTotalAmount())
                .orderStatus("CREATED")
                .productId(orderRequest.getProductId())
                .orderDate(Instant.now())
                .quantity(orderRequest.getQuantity())
                .build();

        order = orderRepository.save(order);

        log.info("OrderServiceImpl | placeOrder | Calling Payment Service to complete the payment");

		/*
		 * PaymentRequest paymentRequest = PaymentRequest.builder()
		 * .orderId(order.getId()) .paymentMode(orderRequest.getPaymentMode())
		 * .amount(orderRequest.getTotalAmount()) .build();
		 */

        String orderStatus = null;

        try {
            log.info("OrderServiceImpl | placeOrder | Payment done Successfully. Changing the Oder status to PLACED");
            orderStatus = "PLACED";
        } catch (Exception e) {
            log.error("OrderServiceImpl | placeOrder | Error occurred in payment. Changing order status to PAYMENT_FAILED");
            orderStatus = "PAYMENT_FAILED";
        }

        order.setOrderStatus(orderStatus);
        
        Order orderDB = orderRepository.findById(order.getId()).orElse(null);
        if (orderDB != null) {
        	orderRepository.save(order);
        }
        
        smsClient.sendSms(new SmsRequest("+91 7722008539","Your Order has been Placed Successfully!!!"));
        emailClient.sendMail(new EmailRequest("mcaamit2013@gmail.com","Your Order has been Placed Successfully!!!"));

        log.info("OrderServiceImpl | placeOrder | Order Places successfully with Order Id: {}", order.getId());
        log.info("OrderServiceImpl | placeOrder | SMS Delivered ");
        log.info("OrderServiceImpl | placeOrder | Email Delivered ");

        return order.getId();
    }

    @Override
    public OrderResponse getOrderDetails(long orderId) {

        log.info("OrderServiceImpl | getOrderDetails | Get order details for Order Id : {}", orderId);

        Order order
                = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderServiceCustomException("Order not found for the order Id:" + orderId,
                        "NOT_FOUND",
                        404));

        log.info("OrderServiceImpl | getOrderDetails | Invoking Product service to fetch the product for id: {}", order.getProductId());
        ProductResponse productResponse
                = restTemplate.getForObject(
                "http://PRODUCT-SERVICE/product/" + order.getProductId(),
                ProductResponse.class
        );

        log.info("OrderServiceImpl | getOrderDetails | Getting payment information form the payment Service");
        PaymentResponse paymentResponse
                = restTemplate.getForObject(
                "http://PAYMENT-SERVICE/payment/order/" + order.getId(),
                PaymentResponse.class
        );

        OrderResponse.ProductDetails productDetails
                = OrderResponse.ProductDetails
                .builder()
                .productName(productResponse.getProductName())
                .productId(productResponse.getProductId())
                .build();

        OrderResponse.PaymentDetails paymentDetails
                = OrderResponse.PaymentDetails
                .builder()
                .paymentId(paymentResponse.getPaymentId())
                .paymentStatus(paymentResponse.getStatus())
                .paymentDate(paymentResponse.getPaymentDate())
                .paymentMode(paymentResponse.getPaymentMode())
                .build();

        OrderResponse orderResponse
                = OrderResponse.builder()
                .orderId(order.getId())
                .orderStatus(order.getOrderStatus())
                .amount(order.getAmount())
                .orderDate(order.getOrderDate())
                .productDetails(productDetails)
                .paymentDetails(paymentDetails)
                .build();

        log.info("OrderServiceImpl | getOrderDetails | orderResponse : " + orderResponse.toString());

        return orderResponse;
    }
}