package com.jahanfoolad.jfs.security;

import com.google.gson.Gson;
import com.jahanfoolad.jfs.domain.ResponseModel;
import com.jahanfoolad.jfs.service.impl.PersonService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;

@Slf4j
public class AuthenticationTokenFilter extends OncePerRequestFilter {


    public static String username;

    @Autowired
    PersonService userService;

    @Autowired
    ResponseModel responseModel;


    DDoSControlService dDoSControlService = new DDoSControlService();


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            username = null;
            username = dDoSControlService.tokenVerification(request);
            if (username != null && userService.findByUserName(username).getIsAuthorizationChanged())
                throw new AuthorizationServiceException("USER AUTHORIZATION HAS CHANGED ");
            chain.doFilter(request, response);

        } catch (AuthorizationServiceException e) {
            ResponseModel responseModel = new ResponseModel();
            responseModel.setResult(-1);
            responseModel.setSystemError(e.getMessage());
            responseModel.setError(e.getMessage());
            responseModel.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.setStatus(HttpStatus.FORBIDDEN.value());
            Gson gson = new Gson();
            response.getWriter().write(gson.toJson(responseModel));
        } catch (Exception e) {
            ResponseModel responseModel = new ResponseModel();
            responseModel.setResult(-1);
            responseModel.setSystemError(e.getCause().toString());
            responseModel.setError(e.getCause().toString());
            responseModel.setContent(new Object());
            responseModel.setContents(new ArrayList<>());
            responseModel.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            Gson gson = new Gson();
            response.getWriter().write(gson.toJson(responseModel));
        }
    }
}