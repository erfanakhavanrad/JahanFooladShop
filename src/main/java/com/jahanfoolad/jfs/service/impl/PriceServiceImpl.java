package com.jahanfoolad.jfs.service.impl;

import com.jahanfoolad.jfs.domain.Price;
import com.jahanfoolad.jfs.domain.dto.PriceDto;
import com.jahanfoolad.jfs.service.PriceService;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public class PriceServiceImpl implements PriceService {
    @Override
    public List<Price> getPrices() throws Exception {
        return null;
    }

    @Override
    public Price getPriceById(Long id) throws Exception {
        return null;
    }

    @Override
    public Price createPrice(PriceDto priceDto, HttpServletRequest httpServletRequest) throws Exception {
        return null;
    }

    @Override
    public Price updatePrice(PriceDto priceDto, HttpServletRequest httpServletRequest) throws Exception {
        return null;
    }

    @Override
    public void deletePrice(Long id) throws Exception {

    }
}
