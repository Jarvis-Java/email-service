package com.shopping.productService.exception;

import lombok.Data;

@Data
public class ProductServiceCustomException extends RuntimeException{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String errorCode;

    public ProductServiceCustomException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}