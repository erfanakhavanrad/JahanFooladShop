package com.jahanfoolad.jfs.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Price")
@Getter
@Setter
public class Price extends AbstractEntity {
    @Column
    private String price;
    @Column
    private Date lastPriceUpdate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinTable(name = "product_price",
            joinColumns =
            @JoinColumn(name = "productattribute_priceList", referencedColumnName = "priceList"),
            inverseJoinColumns =
            @JoinColumn(name = "price_productattribute", referencedColumnName = "productAttribute"))
    private ProductAttribute productAttribute;
}
