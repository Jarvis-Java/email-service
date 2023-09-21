package com.shopping.orderService.orderservice.feignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.shopping.orderService.orderservice.request.EmailRequest;

@FeignClient(name = "EMAIL-SERVICE", url = "http://localhost:8200", path = "/email-service")
public interface EmailClient {

    @GetMapping("/send-mail")
    public String sendMail(@RequestBody EmailRequest emailRequest);
 
}