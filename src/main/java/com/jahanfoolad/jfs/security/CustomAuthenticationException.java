package com.jahanfoolad.jfs.security;

import org.springframework.security.core.AuthenticationException;

/**
 * CustomAuthenticationException here use to pass the message through the "throw new " to AuthenticationException
 */
public class CustomAuthenticationException extends AuthenticationException {
    public CustomAuthenticationException(String msg) {
        super(msg);
    }

    public CustomAuthenticationException(String msg, Throwable cause) {
        super(msg, cause);
    }

}
