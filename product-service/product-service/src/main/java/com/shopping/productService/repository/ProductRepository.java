package com.shopping.productService.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shopping.productService.entity.Product;

public interface ProductRepository extends JpaRepository<Product,Long> {

}