package com.jahanfoolad.jfs.service;

import com.jahanfoolad.jfs.domain.Product;
import com.jahanfoolad.jfs.domain.dto.FileDto;
import com.jahanfoolad.jfs.domain.dto.ProductDto;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface ProductService {
    Page<Product> getProducts(Integer pageNo, Integer perPage) throws Exception;
    Product getProductById(Long id) throws Exception;
    Product createProduct(ProductDto productDto) throws Exception;
    void deleteProduct(Long id) throws  Exception;
    Product updateProduct(ProductDto productDto) throws Exception;
    Page<Product> findByFile(FileDto fileDto, Integer pageNo, Integer perPage);
}
