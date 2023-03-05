package com.shopping.orderService.orderservice.beans;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Component
@NoArgsConstructor
@AllArgsConstructor
public class EmailRequest {
	
	private String toEmailID;
	private String mailText;
	

}
