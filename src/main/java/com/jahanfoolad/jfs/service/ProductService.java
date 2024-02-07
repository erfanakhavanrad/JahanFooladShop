package com.jahanfoolad.jfs.service;

import com.jahanfoolad.jfs.domain.Product;
import com.jahanfoolad.jfs.domain.dto.FileDto;
import com.jahanfoolad.jfs.domain.dto.ProductDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {
    List<Product> getProducts();
    Product getProductById(Long id) throws Exception;
    Product createProduct(ProductDto productDto);
    void deleteProduct(Long id);
    Product updateProduct(ProductDto productDto) throws Exception;
    Page<Product> findByFile(FileDto fileDto, Integer pageNo, Integer perPage);
}
