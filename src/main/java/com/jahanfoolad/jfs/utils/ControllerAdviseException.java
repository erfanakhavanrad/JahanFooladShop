package com.jahanfoolad.jfs.utils;


import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.Locale;

@RestControllerAdvice
public class ControllerAdviseException {

    @Resource(name = "faMessageSource")
    private MessageSource faMessageSource;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public void handleValidationErrors(MethodArgumentNotValidException ex, HttpServletResponse response) throws Exception {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        response.getWriter().println(getErrorModel(response.getStatus(),
                faMessageSource.getMessage("FIELD_VALIDATION_ERROR", null, Locale.ENGLISH),
                ex.getMessage()));
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.getWriter().close();
    }

    @ExceptionHandler(AccessDeniedException.class)
    public void commence(AccessDeniedException accessDeniedException, HttpServletResponse response) throws IOException {
        // 403
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.getWriter().println(getErrorModel(response.getStatus(),
                faMessageSource.getMessage("ACCESS_DENIED", null, Locale.ENGLISH), accessDeniedException.getMessage()));
        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.getWriter().close();
    }

    @ExceptionHandler(Exception.class)
    public void commence(Exception exception, HttpServletResponse response) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        response.getWriter().println(getErrorModel(response.getStatus(),
                faMessageSource.getMessage("UNKNOWN_TRANSACTION_ERROR", null, Locale.ENGLISH),
                exception.getMessage()));
        response.getWriter().close();
    }

    @ExceptionHandler(IllegalArgumentException.class)
    protected void handleConflict(IllegalArgumentException ex, HttpServletResponse response) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        response.getWriter().println(getErrorModel(response.getStatus(),
                faMessageSource.getMessage("FIELD_VALIDATION_ERROR", null, Locale.ENGLISH),
                ex.getMessage()));
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.getWriter().close();
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public void handleMissingParams(MissingServletRequestParameterException ex, HttpServletResponse response) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        String fieldValidationError = MessageFormat.format(
                faMessageSource.getMessage("WRONG_INPUT_PARAM", null, Locale.ENGLISH), ex.getParameterName());
        response.getWriter().println(getErrorModel(response.getStatus(),
                fieldValidationError,
                ex.getParameterName()));
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.getWriter().close();
    }

    public String getErrorModel(int statusRec, String errorMsg, String excMsg) {
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
