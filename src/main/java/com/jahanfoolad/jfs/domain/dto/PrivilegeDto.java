package com.jahanfoolad.jfs.domain.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PrivilegeDto {
    private String name;
    private String domain;
    private String access;
}
