package com.jahanfoolad.jfs.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "Product")
@Getter
@Setter
public class Product extends AbstractEntity {

    @Column
    private String name;
    @Column
    private String code;
    @Column
    private String brand;
    @Column
    private String company;

    @OneToMany(cascade = {CascadeType.ALL})
    private List<File> fileList;

    @OneToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
//    @JoinTable(name = "product_to_attribute",
//            joinColumns =
//            @JoinColumn(name = "product_attribute_list", referencedColumnName = "id"),
//            inverseJoinColumns =
//            @JoinColumn(name = "product", referencedColumnName = "product_list"))
    private List<ProductAttribute> productAttributeList;


    @OneToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id")
    private List<Category> categoryList;


}