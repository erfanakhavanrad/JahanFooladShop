package com.jahanfoolad.jfs.domain.dto;

import com.jahanfoolad.jfs.domain.Category;
import com.jahanfoolad.jfs.domain.File;

import com.jahanfoolad.jfs.domain.ProductAttribute;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class ProductDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 3160483456882362607L;

    private Long   id;
    private String name;
    private String code;
    private String brand;
    private String company;
    private List<File> fileList;
    private List<Category> categoryList;
    private List<ProductAttribute> productAttributeList;
//    private List<Price> priceList;
//    private List<ProductProvider> productProviderList;

}
