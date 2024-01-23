package com.jahanfoolad.jfs.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name="Product")
@Getter
@Setter
public class Product extends AbstractEntity {

    @Column
    private String name;
    @Column
    private String code;
    @Column
    private String length;
    @Column
    private String diameter;
    @Column
    private String unit;
    @Column
    private String baseOfQuantity;
    @OneToMany(cascade = {CascadeType.ALL})
    private List<File> fileList;
//    @OneToMany(cascade = {CascadeType.ALL})
//    private List<Category> categoryList;
//    @OneToMany(cascade = {CascadeType.ALL})
//    private List<Price> priceList;
//    @OneToMany(cascade = {CascadeType.ALL})
//    private List<ProductProvider> productProviderList;

}