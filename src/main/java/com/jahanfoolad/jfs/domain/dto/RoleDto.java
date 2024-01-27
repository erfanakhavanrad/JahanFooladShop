package com.jahanfoolad.jfs.domain.dto;

import com.jahanfoolad.jfs.domain.Privilege;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
public class RoleDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 7901772115064466693L;
    private Privilege privilege;
    private String roleName;

}
