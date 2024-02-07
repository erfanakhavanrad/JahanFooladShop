package com.jahanfoolad.jfs.service.impl;

import com.jahanfoolad.jfs.domain.File;
import com.jahanfoolad.jfs.domain.dto.FileDto;
import com.jahanfoolad.jfs.jpaRepository.FileRepository;
import com.jahanfoolad.jfs.service.FileService;
import jakarta.annotation.Resource;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;

@Service
public class FileServiceImpl implements FileService {

    @Autowired
    FileRepository fileRepository;

    @Resource(name = "faMessageSource")
    private MessageSource faMessageSource;
    @Resource(name = "enMessageSource")
    private MessageSource enMessageSource;

    @Override
    public List<File> getFiles() {
        return fileRepository.findAll();
    }

    @Override
    public File getFileByUserId(Long id) throws Exception {
        return fileRepository.findById(id).orElseThrow ( () -> new Exception(enMessageSource.getMessage("item_not_found_message",null, Locale.ENGLISH)));
    }

    @Override
    public File createFile(FileDto fileDto) {
        ModelMapper modelMapper = new ModelMapper();
        File file = modelMapper.map(fileDto,File.class);
        return modelMapper.map(fileRepository.save(file) , File.class);
    }

    @Override
    public void deleteFile(Long id) {
       fileRepository.deleteById(id);
    }
}
