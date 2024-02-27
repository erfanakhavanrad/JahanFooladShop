package com.jahanfoolad.jfs.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "Product")
@Getter
@Setter
public class Product extends AbstractEntity {

    @Column(unique = true)
    private String name;

    @Column(unique = true)
    private String code;

    @Column
    private String brand;
    @Column
    private Long companyId;

    @OneToMany(cascade = {CascadeType.ALL})
    private List<File> files;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<ProductAttribute> productAttributeList;


    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    private List<Category> categories;


}