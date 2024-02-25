package com.jahanfoolad.jfs.domain.dto;

import com.jahanfoolad.jfs.domain.Category;
import com.jahanfoolad.jfs.domain.File;

import com.jahanfoolad.jfs.domain.ProductAttribute;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
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

    @NotBlank(message = "company id is mandatory")
    private Long companyId;

    private List<File> files;
    private List<Category> categories;
    private List<ProductAttribute> productAttributeList;
}
