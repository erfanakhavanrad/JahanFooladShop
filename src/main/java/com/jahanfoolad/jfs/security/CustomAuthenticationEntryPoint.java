package com.jahanfoolad.jfs.security;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.MessageSource;
import org.springframework.http.MediaType;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.IOException;
import java.io.Serializable;
import java.util.Locale;

@Component
class CustomAuthenticationEntryPoint extends ResponseEntityExceptionHandler implements AuthenticationEntryPoint, Serializable {

    @Resource(name = "faMessageSource")
    private MessageSource faMessageSource;

    @Override
    @ExceptionHandler(value = {AuthorizationServiceException.class})
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException {
        // 401
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().println(getErrorModel(response.getStatus(), faMessageSource.getMessage("TOKEN_NOT_VALID", null, Locale.ENGLISH), authException.getMessage()));
        response.getWriter().close();
    }


    public String getErrorModel(int statusRec, String errorMsg, String excMsg) {
        /*
         Here we make string in json wise because we can not pass the object of model to getOutputStream().println
         */
        String status = "status";
        String recordCount = "recordCount";
        String content = "content";
        String contents = "contents";
        String error = "error";
        String result = "result";
        String systemError = "systemError";
        String strParam = "";

        strParam += "{\"" + status + "\":\"" + statusRec + "\"";
        strParam += ",\"" + recordCount + "\":\"" + 0 + "\"";
        strParam += ",\"" + contents + "\":\"" + "null" + "\"";
        strParam += ",\"" + content + "\":\"" + "null" + "\"";
        strParam += ",\"" + error + "\":\"" + errorMsg + "\"";
        strParam += ",\"" + result + "\":\"" + -1 + "\"";
        strParam += ",\"" + systemError + "\":\"" + excMsg + "\"" + "}";

        return strParam;
    }
}