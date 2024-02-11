package com.jahanfoolad.jfs.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jahanfoolad.jfs.domain.ResponseModel;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import java.io.IOException;


/**
 * CustomAuthenticationFailureHandler  here use to handle the Authentication fail exception and pass the custom msg
 */

@Slf4j
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException exception)
            throws IOException, ServletException {

        ResponseModel responseModel = new ResponseModel();
        responseModel.setResult(-1);
        responseModel.setSystemError(exception.getMessage());
        responseModel.setStatus(HttpServletResponse.SC_FORBIDDEN);
        new ObjectMapper().writeValue(response.getOutputStream(), responseModel);
    }

}