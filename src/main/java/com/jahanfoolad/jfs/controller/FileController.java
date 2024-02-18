package com.jahanfoolad.jfs.controller;


import com.jahanfoolad.jfs.domain.File;
import com.jahanfoolad.jfs.domain.ResponseModel;
import com.jahanfoolad.jfs.domain.dto.FileDto;
import com.jahanfoolad.jfs.service.FileService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;

@Slf4j
@RequestMapping("/file")
@RestController
public class FileController {

    @Autowired
    FileService fileService;

    @Autowired
    ResponseModel responseModel;

    @Resource(name = "faMessageSource")
    private MessageSource faMessageSource;

    @Value("${SUCCESS_RESULT}")
    int success;

    @Value("${FAIL_RESULT}")
    int fail;

    @GetMapping("/getAll")
    public ResponseModel getFiles(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {

        responseModel.clear();
        try {
            log.info("get file");
            List<File> files = fileService.getFiles();
            responseModel.setContent(files);
            responseModel.setResult(success);
            responseModel.setRecordCount(files.size());
            responseModel.setStatus(httpServletResponse.getStatus());
        } catch (DataIntegrityViolationException dataIntegrityViolationException) {
            responseModel.setSystemError(dataIntegrityViolationException.getMessage());
            responseModel.setError(faMessageSource.getMessage("ALREADY_NOT_EXISTS",null, Locale.ENGLISH));
        } catch (Exception e) {
            responseModel.setError(e.getMessage());
        } finally {
            responseModel.setStatus(httpServletResponse.getStatus());
            responseModel.setResult(fail);
        }
        return responseModel;
    }

    @GetMapping("/getbyid")
    public ResponseModel getFileByUserId(@RequestParam Long id, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {

        responseModel.clear();

        try {
            log.info("get file by user id");
            responseModel.setContent(fileService.getFileByUserId(id));
            responseModel.setResult(success);
        } catch (DataIntegrityViolationException dataIntegrityViolationException) {
            responseModel.setSystemError(dataIntegrityViolationException.getMessage());
            responseModel.setError(faMessageSource.getMessage("ALREADY_NOT_EXISTS",null, Locale.ENGLISH));
        } catch (Exception e) {
            responseModel.setError(e.getMessage());
        } finally {
            responseModel.setStatus(httpServletResponse.getStatus());
            responseModel.setResult(fail);
        }
        return responseModel;
    }

    @PostMapping("/save")
    public ResponseModel createFile(@RequestBody FileDto fileDto, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        responseModel.clear();

        try {
            log.info("create file");
            responseModel.setContent(fileService.createFile(fileDto));
            responseModel.setResult(success);
        } catch (DataIntegrityViolationException dataIntegrityViolationException) {
            responseModel.setSystemError(dataIntegrityViolationException.getMessage());
            responseModel.setError(faMessageSource.getMessage("ALREADY_EXISTS",null, Locale.ENGLISH));
        } catch (Exception e) {
            responseModel.setError(e.getMessage());
        } finally {
            responseModel.setStatus(httpServletResponse.getStatus());
            responseModel.setResult(fail);
        }
        return responseModel;

    }

    @DeleteMapping("/delete/{id}")
    public ResponseModel deleteFile(@PathVariable("id") Long id, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        responseModel.clear();

        try {
            log.info("delete file");
            fileService.deleteFile(id);
            responseModel.setResult(success);
        } catch (Exception e) {
            responseModel.setError(e.getMessage());
        } finally {
            responseModel.setStatus(httpServletResponse.getStatus());
            responseModel.setResult(fail);
        }
        return responseModel;
    }

    @PutMapping("/update")
    public ResponseModel updateFile(@RequestBody FileDto fileDto , HttpServletRequest httpServletRequest , HttpServletResponse httpServletResponse){

        try{
            log.info("update file");
            responseModel.setContent(fileService.updateFile(fileDto));
            responseModel.setResult(success);
        }catch (DataIntegrityViolationException dataIntegrityViolationException){
            responseModel.setSystemError(dataIntegrityViolationException.getMessage());
            responseModel.setError(faMessageSource.getMessage("ALREADY_EXISTS",null, Locale.ENGLISH));
        }catch (Exception e){
            responseModel.setError(e.getMessage());
        } finally {
            responseModel.setStatus(httpServletResponse.getStatus());
            responseModel.setResult(fail);
        }
        return  responseModel;
    }

}
