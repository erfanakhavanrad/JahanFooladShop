package com.jahanfoolad.jfs.domain.dto;

import com.jahanfoolad.jfs.domain.Price;
import com.jahanfoolad.jfs.domain.Product;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class ProductAttributeDto implements Serializable {

    @Serial
    private static final long serialVersionUID = -1234852588858524421L;
    private Double length;
    private Double diameter;
    private Double width;
    private Double weight;
    private Double size;
    private String unit;
    private String type;
    private Double thickness;
    private String standard;
    private String state;
    private String alloy;
    private String condition;
    private String deliveryPlace;
    private List<Price> priceList;
//    private Product productList;
}
