package com.shopping.orderService.orderservice.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.shopping.orderService.orderservice.beans.EmailRequest;
import com.shopping.orderService.orderservice.services.MailService;

@RestController
public class EmailController {

	@Autowired
	private MailService mailService;
	
	@PostMapping("/send-mail")
    public String sendMail(@RequestBody EmailRequest emailRequest) {

    	try {
			return mailService.sendMail(emailRequest.getToEmailID(), emailRequest.getMailText());
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    	return null;
    }
}
