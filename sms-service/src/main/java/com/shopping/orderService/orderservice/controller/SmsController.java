package com.shopping.orderService.orderservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shopping.orderService.orderservice.beans.SmsRequest;
import com.shopping.orderService.orderservice.services.SMSSender;

@RestController
@RequestMapping("/sms-service")
public class SmsController {

    @Autowired
    private SMSSender smsService;

    @PostMapping("/send-sms")
    public String sendSms(@RequestBody SmsRequest smsRequest) {

    	return smsService.sendSMS(smsRequest.getMobileNumber(), smsRequest.getMessageText());

    }
}
