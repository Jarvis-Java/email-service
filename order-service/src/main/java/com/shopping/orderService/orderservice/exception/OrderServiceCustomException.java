package com.shopping.orderService.orderservice.exception;

import lombok.Data;

@Data
public class OrderServiceCustomException extends RuntimeException{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String errorCode;
    private int status;

    public OrderServiceCustomException(String message, String errorCode, int status) {
        super(message);
        this.errorCode = errorCode;
        this.status = status;
    }
}