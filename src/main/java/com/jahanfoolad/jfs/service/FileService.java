package com.jahanfoolad.jfs.service;

import com.jahanfoolad.jfs.domain.File;
import com.jahanfoolad.jfs.domain.ResponseModel;
import com.jahanfoolad.jfs.domain.dto.FileDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    Page<File> getFiles( Integer pageNo, Integer perPage) throws Exception;
    File getFileByUserId(Long id) throws Exception;
    ResponseModel createFile(MultipartFile file , String title, HttpServletRequest httpServletRequest);
    ResponseModel deleteFile(Long id) throws Exception;
    File updateFile(FileDto fileDto, HttpServletRequest httpServletRequest) throws Exception;
}
