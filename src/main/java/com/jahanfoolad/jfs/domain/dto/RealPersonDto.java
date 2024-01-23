package com.jahanfoolad.jfs.domain.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class RealPersonDto implements Serializable {

    @Serial
    private static final long serialVersionUID = -4488203080352610579L;

    private Long id;
    private String firstName;
    private String lastName;
    private String nationalNumber;
    private String cellPhone;
    private String email;
    private Boolean verified;
    private Boolean activated;
    private String password;
    private String encryptedPassword;
    private List<ContactDto> contactDtoList;


    /*
    *     private Long id = Long.valueOf(10);
    private String userId;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String encryptedPassword;
    private String emailVerificationToken;
    private Boolean emailVerificationStatus = false;
    private List<AddressDTO> addresses;
    private Collection<String> roles;*/


}
