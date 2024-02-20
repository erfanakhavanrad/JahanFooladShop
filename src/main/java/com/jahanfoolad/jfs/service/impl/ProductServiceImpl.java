package com.jahanfoolad.jfs.service.impl;

import com.jahanfoolad.jfs.JfsApplication;
import com.jahanfoolad.jfs.domain.Product;
import com.jahanfoolad.jfs.domain.ProductAttribute;
import com.jahanfoolad.jfs.domain.ResponseModel;
import com.jahanfoolad.jfs.domain.dto.FileDto;
import com.jahanfoolad.jfs.domain.dto.ProductDto;
import com.jahanfoolad.jfs.jpaRepository.PriceRepository;
import com.jahanfoolad.jfs.jpaRepository.ProductAttributeRepository;
import com.jahanfoolad.jfs.jpaRepository.ProductRepository;
import com.jahanfoolad.jfs.service.ProductAttributeService;
import com.jahanfoolad.jfs.service.ProductService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Slf4j
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProductAttributeRepository productAttributeRepository;

    @Autowired
    ProductAttributeService productAttributeService;

    @Autowired
    PriceRepository priceRepository;

    @Autowired
    ResponseModel responseModel;

    @Resource(name = "faMessageSource")
    private MessageSource faMessageSource;

    @Override
    public Page<Product> getProducts(Integer pageNo, Integer perPage) {
        return productRepository.findAll(JfsApplication.createPagination(pageNo, perPage));
    }

    @Override
    public Product getProductById(Long id) throws Exception {
        return productRepository.findById(id).orElseThrow(() -> new Exception(faMessageSource.getMessage("NOT_FOUND", null, Locale.ENGLISH)));
    }

    @Override
    public Product createProduct(ProductDto productDto) throws Exception {
        ModelMapper modelMapper = new ModelMapper();
        Product product = modelMapper.map(productDto, Product.class);
//        ProductAttribute productAttribute = modelMapper.map(productDto.getProductAttributeList(), ProductAttribute.class);
        List<ProductAttribute> savedProductAttributes = productAttributeService.createProductAttributes(product.getProductAttributeList());
        product.setProductAttributeList(savedProductAttributes);
//        List<ProductAttribute> savedProductAttributes = productAttributeRepository.saveAll(productDto.getProductAttributeList());
//        product.setProductAttributeList(savedProductAttributes);
        return modelMapper.map(productRepository.save(product), Product.class);
    }

    @Override
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public Product updateProduct(ProductDto productDto) throws Exception {
        log.info("update product");
        responseModel.clear();

        ModelMapper modelMapper = new ModelMapper();

        Product foundProduct = getProductById(productDto.getId());
        Product newProduct = modelMapper.map(productDto, Product.class);
        Product updated = (Product) responseModel.merge(foundProduct, newProduct);

        if (newProduct.getFileList() != null && !newProduct.getFileList().isEmpty()) {
            updated.setFileList(newProduct.getFileList());
        }
        return productRepository.save(updated);
    }

    @Override
    public Page<Product> findByFile(FileDto fileDto, Integer pageNo, Integer perPage) {
        log.info("find product By File");
        ModelMapper modelMapper = new ModelMapper();
        Product product = modelMapper.map(fileDto, Product.class);
        product.setId(702l);
        List<Product> productList = new ArrayList<>();
        productList.add(product);
        return productRepository.findAllByFileListIn(productList, PageRequest.of(pageNo, perPage));
    }
}
