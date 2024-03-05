package com.jahanfoolad.jfs.domain.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class CompanyDto implements Serializable {


    @Serial
    private static final long serialVersionUID = -569153872648535136L;

    private Long   id;
    private String name;
    private String nationalId;
    private String economicCode;
    private String agentName;
    private String cellphone;
    private String email;
    private Boolean verified;
    private Boolean isActive;
    private List<ContactDto> contactList;
    private List<FileDto> fileList;
    private List<ProductDto> productList;
    private List<CorpPersonDto> corpPersonList;
//    private List<Role> roleList;

}
