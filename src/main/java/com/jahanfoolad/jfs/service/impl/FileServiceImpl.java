package com.jahanfoolad.jfs.service.impl;

import com.jahanfoolad.jfs.domain.File;
import com.jahanfoolad.jfs.domain.dto.FileDto;
import com.jahanfoolad.jfs.jpaRepository.FileRepository;
import com.jahanfoolad.jfs.service.FileService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileServiceImpl implements FileService {

    @Autowired
    FileRepository fileRepository;

    @Override
    public List<File> getFiles() {
        return fileRepository.findAll();
    }

    @Override
    public File getFileByUserId(Long id) throws Exception {
        return fileRepository.findById(id).orElseThrow ( () -> new Exception("company not found"));
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
