package com.jahanfoolad.jfs.domain.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class CorpPersonDto implements Serializable {
    @Serial
    private static final long serialVersionUID = -5811526900658221527L;
    private String name;
    private String nationalId;
    private String economicCode;
    private String agentName;
    private String cellPhone;
    private String email;
    private Boolean verified;
    private Boolean isActive;
    private List<ContactDto> contactList;

}
