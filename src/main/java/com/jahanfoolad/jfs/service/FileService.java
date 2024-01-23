package com.jahanfoolad.jfs.service;

import com.jahanfoolad.jfs.domain.File;
import com.jahanfoolad.jfs.domain.dto.FileDto;

import java.util.List;

public interface FileService {
    List<File> getFiles();
    File getFileByUserId(Long id) throws Exception;
    File createFile(FileDto fileDto);
    void deleteFile(Long id);
}
