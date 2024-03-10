package com.jahanfoolad.jfs.service;

import com.jahanfoolad.jfs.domain.Product;
import com.jahanfoolad.jfs.domain.dto.FileDto;
import com.jahanfoolad.jfs.domain.dto.PriceDto;
import com.jahanfoolad.jfs.domain.dto.ProductDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;

public interface ProductService {
    Page<Product> getProducts(Integer pageNo, Integer perPage) throws Exception;

    Product getProductById(Long id) throws Exception;

    Product createProduct(ProductDto productDto, HttpServletRequest httpServletRequest) throws Exception;

    void deleteProduct(Long id) throws Exception;

    Product updateProduct(ProductDto productDto) throws Exception;

    Page<Product> findByFile(FileDto fileDto, Integer pageNo, Integer perPage);

    Product addPrice(PriceDto priceDto, Long productId, Long attributeId , HttpServletRequest httpServletRequest) throws Exception;
}
