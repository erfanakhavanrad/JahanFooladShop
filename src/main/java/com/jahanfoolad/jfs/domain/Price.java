package com.jahanfoolad.jfs.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "Price")
@Getter
@Setter
public class Price extends AbstractEntity {
    @Column
    private String price;
    @Column
    private Date lastPriceUpdate;
}
