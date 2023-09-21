package com.shopping.productService.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductRequest {
    private String name;
    private long price;
    private long quantity;
}