package com.jahanfoolad.jfs.security;

import org.springframework.security.core.GrantedAuthority;

public class GrantedAuthorityImpl implements GrantedAuthority {


    String Authority;

    public GrantedAuthorityImpl(String autho) {
        this.Authority = autho;
    }

    @Override
    public String getAuthority() {
        return Authority;
    }
}
