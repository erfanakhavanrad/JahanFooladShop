package com.jahanfoolad.jfs.utils;


import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.ConstraintViolationException;
import org.springframework.context.MessageSource;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ControllerAdviseException {

    @Resource(name = "faMessageSource")
    private MessageSource faMessageSource;

    @ExceptionHandler
    public void handle(ConstraintViolationException exception , HttpServletResponse response) throws Exception{
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        String errorMessage = new ArrayList<>(exception.getConstraintViolations()).get(0).getMessage();
        response.getOutputStream().println(getErrorModel(response.getStatus(), errorMessage, faMessageSource.getMessage("FIELD_VALIDATION_ERROR" , null , Locale.ENGLISH)));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public void handleValidationErrors(MethodArgumentNotValidException ex , HttpServletResponse response) throws Exception{
        List<String> errors = ex.getBindingResult().getFieldErrors()
                .stream().map(FieldError::getDefaultMessage).collect(Collectors.toList());

        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        String errorMessage = new ArrayList<>(errors).get(0);
        response.getOutputStream().println(getErrorModel(response.getStatus(), errorMessage,  faMessageSource.getMessage("FIELD_VALIDATION_ERROR" , null , Locale.ENGLISH)));
   }



    public String getErrorModel(int statusRec, String errorMsg , String excMsg) {
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
