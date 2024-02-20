package com.jahanfoolad.jfs.service;

import com.jahanfoolad.jfs.domain.ProductAttribute;
import com.jahanfoolad.jfs.domain.dto.ProductAttributeDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductAttributeService {
    Page<ProductAttribute> getProductAttributes(Integer pageNo, Integer perPage) throws Exception;
    ProductAttribute getProductAttributeById(Long id) throws Exception;
    ProductAttribute createProductAttribute(ProductAttributeDto productAttributeDto, HttpServletRequest httpServletRequest) throws Exception;
    List<ProductAttribute> createProductAttributes(List<ProductAttribute> productAttribute) throws Exception;
    void deleteProductAttribute(Long id);

}
