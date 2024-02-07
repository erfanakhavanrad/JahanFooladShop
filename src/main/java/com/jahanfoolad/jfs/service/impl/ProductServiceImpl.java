package com.jahanfoolad.jfs.service.impl;

import com.jahanfoolad.jfs.domain.Product;
import com.jahanfoolad.jfs.domain.dto.ProductDto;
import com.jahanfoolad.jfs.jpaRepository.ProductRepository;
import com.jahanfoolad.jfs.service.ProductService;
import jakarta.annotation.Resource;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository productRepository;

    @Resource(name = "faMessageSource")
    private MessageSource faMessageSource;
    @Resource(name = "enMessageSource")
    private MessageSource enMessageSource;

    @Override
    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product getProductByUserId(Long id) throws Exception {
        return productRepository.findById(id).orElseThrow ( () -> new Exception(enMessageSource.getMessage("item_not_found_message",null, Locale.ENGLISH)));
    }

    @Override
    public Product createProduct(ProductDto productDto) {
        ModelMapper modelMapper = new ModelMapper();
        Product product = modelMapper.map(productDto,Product.class);
        return modelMapper.map(productRepository.save(product) , Product.class);
    }

    @Override
    public void deleteProduct(Long id) {
         productRepository.deleteById(id);
    }
}
