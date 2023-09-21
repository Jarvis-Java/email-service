package com.shopping.orderService.orderservice.services;

public class SmsTest {

	public static void main(String[] args) {
		
		SMSSender smsSender = new SMSSender();
		
		smsSender.sendSMS("+91 7722008539", "Main Test");
	}

}
