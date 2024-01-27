package com.jahanfoolad.jfs.domain.dto;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
public class ContactDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 4230281807623583094L;

    private String province;
    private String city;
    private String street;
    private String alley;
    private String number;
    private String unit;
    private String postalCode;
    private String telephone;


}
