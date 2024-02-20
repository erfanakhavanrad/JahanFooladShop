package com.jahanfoolad.jfs.domain.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
public class PriceDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 8652231045388150017L;
    private String price;
    private Date lastPriceUpdate;
}
