package com.shopping.orderService.orderservice.feignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.shopping.orderService.orderservice.request.SmsRequest;

@FeignClient(name = "SMS-SERVICE", url = "http://localhost:8300", path = "/sms-service")
public interface SMSClient {

    @GetMapping("/send-sms")
    public String sendSms(@RequestBody SmsRequest smsRequest);
 
}
