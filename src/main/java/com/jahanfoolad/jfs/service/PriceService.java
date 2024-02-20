package com.jahanfoolad.jfs.service;

import com.jahanfoolad.jfs.domain.Price;
import com.jahanfoolad.jfs.domain.dto.PriceDto;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface PriceService {
    List<Price> getPrices() throws Exception;

    Price getPriceById(Long id) throws Exception;

    Price createPrice(PriceDto priceDto, HttpServletRequest httpServletRequest) throws Exception;

    Price updatePrice(PriceDto priceDto, HttpServletRequest httpServletRequest) throws Exception;

    void deletePrice(Long id) throws Exception;
}
