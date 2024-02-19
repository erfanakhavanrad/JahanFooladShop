package com.jahanfoolad.jfs.service;

import com.jahanfoolad.jfs.domain.File;
import com.jahanfoolad.jfs.domain.dto.FileDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;

public interface FileService {
    Page<File> getFiles( Integer pageNo, Integer perPage) throws Exception;
    File getFileByUserId(Long id) throws Exception;
    File createFile(FileDto fileDto, HttpServletRequest httpServletRequest) throws Exception;
    void deleteFile(Long id) throws Exception;
    File updateFile(FileDto fileDto, HttpServletRequest httpServletRequest) throws Exception;
}
