package com.jahanfoolad.jfs.service.impl;

import com.jahanfoolad.jfs.JfsApplication;
import com.jahanfoolad.jfs.domain.Price;
import com.jahanfoolad.jfs.domain.ProductAttribute;
import com.jahanfoolad.jfs.domain.ResponseModel;
import com.jahanfoolad.jfs.domain.dto.ProductAttributeDto;
import com.jahanfoolad.jfs.jpaRepository.PriceRepository;
import com.jahanfoolad.jfs.jpaRepository.ProductAttributeRepository;
import com.jahanfoolad.jfs.service.ProductAttributeService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;

@Service
public class ProductAttributeServiceImpl implements ProductAttributeService {

    @Autowired
    ProductAttributeRepository productAttributeRepository;

    @Autowired
    PriceRepository priceRepository;

    @Autowired
    ResponseModel responseModel;

    @Resource(name = "faMessageSource")
    private MessageSource faMessageSource;


    @Override
    public Page<ProductAttribute> getProductAttributes(Integer pageNo, Integer perPage) throws Exception {
        return productAttributeRepository.findAll(JfsApplication.createPagination(pageNo, perPage));
    }

    @Override
    public ProductAttribute getProductAttributeById(Long id) throws Exception {
        return productAttributeRepository.findById(id).orElseThrow(() -> new Exception(faMessageSource.getMessage("NOT_FOUND", null, Locale.ENGLISH)));
    }

    @Override
    public ProductAttribute createProductAttribute(ProductAttributeDto productAttributeDto, HttpServletRequest httpServletRequest) throws Exception {
        ModelMapper modelMapper = new ModelMapper();
        ProductAttribute productAttribute = modelMapper.map(productAttributeDto, ProductAttribute.class);
        return modelMapper.map(productAttributeRepository.save(productAttribute), ProductAttribute.class);
    }

    @Override
    public List<ProductAttribute> createProductAttributes(List<ProductAttribute> productAttributeList) throws Exception {
        for (ProductAttribute productAttribute : productAttributeList) {
            savePrice(productAttribute.getPriceList());
        }
        return productAttributeRepository.saveAll(productAttributeList);
    }

    private List<Price> savePrice(List<Price> priceList) {
        return priceRepository.saveAll(priceList);
    }

    @Override
    public void deleteProductAttribute(Long id) {
        productAttributeRepository.deleteById(id);
    }
}
