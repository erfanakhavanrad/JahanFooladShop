package com.jahanfoolad.jfs.domain.dto;

import com.jahanfoolad.jfs.domain.File;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
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

    private String name;
    private String code;
    private String length;
    private String diameter;
    private String unit;
    private String baseOfQuantity;
    private List<File> fileList;
//    private List<Category> categoryList;
//    private List<Price> priceList;
//    private List<ProductProvider> productProviderList;

}
