package com.jahanfoolad.jfs.service.impl;

import com.jahanfoolad.jfs.JfsApplication;
import com.jahanfoolad.jfs.domain.*;
import com.jahanfoolad.jfs.domain.dto.PriceDto;
import com.jahanfoolad.jfs.jpaRepository.PriceRepository;
import com.jahanfoolad.jfs.security.SecurityService;
import com.jahanfoolad.jfs.service.PriceService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;

@Slf4j
@Service
public class PriceServiceImpl implements PriceService {

    @Autowired
    PriceService priceService;

    @Autowired
    PriceRepository priceRepository;

    @Autowired
    SecurityService securityService;

    @Autowired
    ResponseModel responseModel;

    @Resource(name = "faMessageSource")
    private MessageSource faMessageSource;

    @Override
    public Page<Price> getPrices(Integer pageNo, Integer perPage) {
        return priceRepository.findAll(JfsApplication.createPagination(pageNo, perPage));
    }

    @Override
    public Price getPriceById(Long id) throws Exception {
        return priceRepository.findById(id).orElseThrow(() -> new Exception(faMessageSource.getMessage("PRODUCT_NOT_FOUND", null, Locale.ENGLISH)));
    }

    @Override
    public Price createPrice(PriceDto priceDto, HttpServletRequest httpServletRequest) throws Exception {
        ModelMapper modelMapper = new ModelMapper();
        Price price = modelMapper.map(priceDto, Price.class);
        price.setCreatedBy((((Person) securityService.getUserByToken(httpServletRequest).getContent()).getId()));
        return priceRepository.save(price);
    }

    @Override
    public Price updatePrice(PriceDto priceDto, HttpServletRequest httpServletRequest) throws Exception {
        log.info("update file");
        responseModel.clear();
        ModelMapper modelMapper = new ModelMapper();
        Price foundPrice = getPriceById(priceDto.getId());
        Price newPrice = modelMapper.map(priceDto, Price.class);
        Price updated = (Price) responseModel.merge(foundPrice, newPrice);
        return priceRepository.save(updated);
    }

    @Override
    public void deletePrice(Long id) throws Exception {
        priceRepository.deleteById(id);
    }
}
