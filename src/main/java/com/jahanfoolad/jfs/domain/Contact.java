package com.jahanfoolad.jfs.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "contact")
@Getter
@Setter
public class Contact extends AbstractEntity {

    @Column
    private String province;
    @Column
    private String city;
    @Column
    private String street;
    @Column
    private String alley;
    @Column
    private String number;
    @Column
    private String unit;
    @Column
    private String postalCode;
    @Column
    private String telephone;

}
