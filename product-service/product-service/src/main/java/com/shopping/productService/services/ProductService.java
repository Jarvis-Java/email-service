package com.shopping.productService.services;

import java.util.List;

import com.shopping.productService.request.ProductRequest;
import com.shopping.productService.response.ProductResponse;

public interface ProductService {

    public long addProduct(ProductRequest productRequest);

    public ProductResponse getProductById(long productId);

    public void reduceQuantity(long productId, long quantity);

    public void deleteProductById(long productId);

	public List<ProductResponse> getAllProducts();
}