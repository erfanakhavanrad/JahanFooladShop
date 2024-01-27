package com.jahanfoolad.jfs.service;

import com.jahanfoolad.jfs.domain.Product;
import com.jahanfoolad.jfs.domain.dto.ProductDto;

import java.util.List;

public interface ProductService {
    List<Product> getProducts();
    Product getProductByUserId(Long id) throws Exception;
    Product createProduct(ProductDto productDto);
    void deleteProduct(Long id);
}
