package com.shopping.orderService.orderservice.services;

import org.springframework.stereotype.Service;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

@Service
public class SMSSender {

	//@Value("${jarvis.sms.accountsid}")
    private String accountSID="AC9a1ffab9020c917bc6cf22b4c897411d";
	
	//@Value("${jarvis.sms.accountsid}")
    private String authToken="91e980c8b7cb34394851dada1e7cdac9";
	
	//@Value("${jarvis.sms.fromNumber}")
	private String fromNumber="+15674122849";
	

    public String sendSMS(String toNumber, String messageText) {
        Twilio.init(accountSID, authToken);

        Message message = Message.creator(
                new PhoneNumber(toNumber),
                new PhoneNumber(fromNumber),
                messageText).create();

        return "Sent SMS message with ID: " + message.getSid();
    }
}


