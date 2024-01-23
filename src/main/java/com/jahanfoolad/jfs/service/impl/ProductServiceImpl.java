package com.jahanfoolad.jfs.service.impl;

import com.jahanfoolad.jfs.domain.Product;
import com.jahanfoolad.jfs.domain.dto.ProductDto;
import com.jahanfoolad.jfs.jpaRepository.ProductRepository;
import com.jahanfoolad.jfs.service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository productRepository;

    @Override
    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product getProductByUserId(Long id) throws Exception {
        return productRepository.findById(id).orElseThrow ( () -> new Exception("company not found"));
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
