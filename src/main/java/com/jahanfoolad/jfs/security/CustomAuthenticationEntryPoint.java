package com.jahanfoolad.jfs.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Component
class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint, Serializable {


    @Override
    @ExceptionHandler(value = {AuthorizationServiceException.class})
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException {
        // 401
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//        response.getOutputStream().println(getErrorModel(response.getStatus(), requirementsProperties.tokenNotValid(), authException.getMessage()));
    }

    @ExceptionHandler(value = {AccessDeniedException.class})
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AccessDeniedException accessDeniedException) throws IOException {
        // 403
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
//        response.getOutputStream().println(getErrorModel(response.getStatus(), requirementsProperties.accessDenied(), accessDeniedException.getMessage()));
    }

    @ExceptionHandler(value = {Exception.class})
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         Exception exception) throws IOException {
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
//        response.getOutputStream().println(getErrorModel(response.getStatus(), requirementsProperties.unknownError(), exception.getMessage()));
    }
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
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