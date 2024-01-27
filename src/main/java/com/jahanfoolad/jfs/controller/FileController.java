package com.jahanfoolad.jfs.controller;


import com.jahanfoolad.jfs.domain.File;
import com.jahanfoolad.jfs.domain.ResponseModel;
import com.jahanfoolad.jfs.domain.dto.FileDto;
import com.jahanfoolad.jfs.service.FileService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequestMapping("/file")
@RestController
public class FileController {

    @Autowired
    FileService fileService;

    @Autowired
    ResponseModel responseModel;

    @GetMapping("/getAll")
    public ResponseModel getFiles(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){

        responseModel.clear();
        try {
            log.info("get file");
            List<File> files = fileService.getFiles();
            responseModel.setContent(files);
            responseModel.setResult(1);
            responseModel.setRecordCount(files.size());
            responseModel.setStatus(httpServletResponse.getStatus());
        }catch(DataIntegrityViolationException dataIntegrityViolationException){
            responseModel.setError(dataIntegrityViolationException.getMessage());
        }catch(Exception e){
            responseModel.setError(e.getMessage());
        } finally {
            responseModel.setStatus(httpServletResponse.getStatus());
            responseModel.setResult(0);
        }
        return responseModel;
    }

    @GetMapping("/getbyid")
    public ResponseModel getFileByUserId(@RequestParam Long id, HttpServletRequest httpServletRequest , HttpServletResponse httpServletResponse){

        responseModel.clear();

        try {
            log.info("get file by user id");
            responseModel.setContent(fileService.getFileByUserId(id));
            responseModel.setResult(1);
        }catch(DataIntegrityViolationException dataIntegrityViolationException){
            responseModel.setError(dataIntegrityViolationException.getMessage());
        }catch(Exception e){
            responseModel.setError(e.getMessage());
        } finally {
            responseModel.setStatus(httpServletResponse.getStatus());
            responseModel.setResult(0);
        }
        return responseModel;
    }

    @PostMapping("/save")
    public ResponseModel createFile(@RequestBody FileDto fileDto , HttpServletRequest httpServletRequest , HttpServletResponse httpServletResponse ){
        responseModel.clear();

        try {
            log.info("create file");
            responseModel.setContent(fileService.createFile(fileDto));
            responseModel.setResult(1);
        }catch(DataIntegrityViolationException dataIntegrityViolationException){
            responseModel.setError(dataIntegrityViolationException.getMessage());
        }catch(Exception e){
            responseModel.setError(e.getMessage());
        } finally {
            responseModel.setStatus(httpServletResponse.getStatus());
            responseModel.setResult(0);
        }
        return responseModel;

    }

    @DeleteMapping("/delete/{id}")
    public ResponseModel deleteFile(@PathVariable("id") Long id, HttpServletRequest httpServletRequest , HttpServletResponse httpServletResponse){
        responseModel.clear();

        try {
            log.info("delete file");
            fileService.deleteFile(id);
            responseModel.setResult(1);
        }catch(DataIntegrityViolationException dataIntegrityViolationException){
            responseModel.setError(dataIntegrityViolationException.getMessage());
        }catch(Exception e){
            responseModel.setError(e.getMessage());
        } finally {
            responseModel.setStatus(httpServletResponse.getStatus());
            responseModel.setResult(0);
        }
        return responseModel;
    }

}
