package com.jahanfoolad.jfs.service.impl;

import com.jahanfoolad.jfs.JfsApplication;
import com.jahanfoolad.jfs.domain.File;
import com.jahanfoolad.jfs.domain.ResponseModel;
import com.jahanfoolad.jfs.domain.dto.FileDto;
import com.jahanfoolad.jfs.jpaRepository.FileRepository;
import com.jahanfoolad.jfs.service.FileService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Slf4j
@Service
public class FileServiceImpl implements FileService {

    @Autowired
    FileRepository fileRepository;

    @Autowired
    ResponseModel responseModel;

    @Resource(name = "faMessageSource")
    private MessageSource faMessageSource;


    @Override
    public Page<File> getFiles(Integer pageNo, Integer perPage) {
        return fileRepository.findAll(JfsApplication.createPagination(pageNo,perPage));
    }

    @Override
    public File getFileByUserId(Long id) throws Exception {
        return fileRepository.findById(id).orElseThrow ( () -> new Exception(faMessageSource.getMessage("NOT_FOUND",null, Locale.ENGLISH)));
    }

    @Override
    public File createFile(FileDto fileDto, HttpServletRequest httpServletRequest) {
        ModelMapper modelMapper = new ModelMapper();
        File file = modelMapper.map(fileDto,File.class);
        return modelMapper.map(fileRepository.save(file) , File.class);
    }

    @Override
    public void deleteFile(Long id) {
       fileRepository.deleteById(id);
    }

    @Override
    public File updateFile(FileDto fileDto, HttpServletRequest httpServletRequest) throws Exception {
        log.info("update file");
        responseModel.clear();

        ModelMapper modelMapper = new ModelMapper();

        File foundFile =getFileByUserId(fileDto.getId());
        File newFile = modelMapper.map(fileDto,File.class);
        File updated = (File) responseModel.merge(foundFile,newFile);

        return fileRepository.save(updated);
    }
}
