package com.jahanfoolad.jfs.domain.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
public class PrivilegeDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 8557168963899819861L;
    private Long id;
    private String name;
    private String domain;
    private String access;
}
