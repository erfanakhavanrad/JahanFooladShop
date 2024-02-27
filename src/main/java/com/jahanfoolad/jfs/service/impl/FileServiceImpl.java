package com.jahanfoolad.jfs.service.impl;

import com.jahanfoolad.jfs.JfsApplication;
import com.jahanfoolad.jfs.domain.File;
import com.jahanfoolad.jfs.domain.Person;
import com.jahanfoolad.jfs.domain.ResponseModel;
import com.jahanfoolad.jfs.domain.dto.FileDto;
import com.jahanfoolad.jfs.jpaRepository.FileRepository;
import com.jahanfoolad.jfs.security.SecurityService;
import com.jahanfoolad.jfs.service.FileService;
import com.jahanfoolad.jfs.utils.CustomException;
import jakarta.annotation.Resource;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

@Slf4j
@Service
public class FileServiceImpl implements FileService {

    @Autowired
    FileRepository fileRepository;

    @Autowired
    ResponseModel responseModel;

    @Autowired
    private SecurityService securityService;

    @Resource(name = "faMessageSource")
    private MessageSource faMessageSource;

    static String BASE_DIRECTORY = "/var/www/jfs/files/";
    static String BASE_IMAGE_DIRECTORY = BASE_DIRECTORY + "images/";
    static String USER_PROFILE_DB = "/images/profiles/";

    @Value("${SUCCESS_RESULT}")
    int success;

    @Value("${FAIL_RESULT}")
    int fail;

    @Override
    public Page<File> getFiles(Integer pageNo, Integer perPage) {
        return fileRepository.findAll(JfsApplication.createPagination(pageNo, perPage));
    }

    @Override
    public File getFileByUserId(Long id) throws Exception {
        return fileRepository.findById(id).orElseThrow(() -> new Exception(faMessageSource.getMessage("NOT_FOUND", null, Locale.ENGLISH)));
    }

    //    @PreAuthorize("hasPermission(#id,'FILE', 'WRITE')")
    @Override
    public ResponseModel createFile(MultipartFile file, String title, HttpServletRequest request) {
        return copyFile(BASE_DIRECTORY, file, title, request);
    }


    @Override
    public File updateFile(FileDto fileDto, HttpServletRequest httpServletRequest) throws Exception {
        log.info("update file");
        responseModel.clear();
        ModelMapper modelMapper = new ModelMapper();
        File foundFile = getFileByUserId(fileDto.getId());
        File newFile = modelMapper.map(fileDto, File.class);
        File updated = (File) responseModel.merge(foundFile, newFile);
        return fileRepository.save(updated);
    }

    //    @PreAuthorize("hasPermission(#id,'FILE', 'WRITE')")
    public ResponseModel save(MultipartFile files, String title) {
        responseModel.clear();
        return copyFile(BASE_DIRECTORY, files, title, null);
    }

    @PreAuthorize("hasPermission(#id,'FILE', 'DELETE')")
    @Override
    public ResponseModel deleteFile(Long fileId) {
        responseModel.clear();
        try {

            File file = fileRepository.findById(fileId).orElseThrow(
                    () -> new CustomException(faMessageSource.getMessage("FILE_NOT_FOUND", null, Locale.ENGLISH)));// file not found
            remove(file);
            String BASE_FOLDER = BASE_DIRECTORY;
            ProcessBuilder builder = new ProcessBuilder();
            builder.command("sh", "-c", "rm -r  " + file.getName() + "." + file.getExtension());
            builder.directory(new java.io.File(BASE_FOLDER));
            Process process = builder.start();
            StreamGobbler streamGobbler = new StreamGobbler(process.getInputStream(), System.out::println);
            Executors.newSingleThreadExecutor().submit(streamGobbler);
            process.waitFor();

            responseModel.setResult(success);
            return responseModel;
        } catch (DataIntegrityViolationException constraintViolationException) {
            responseModel.setError(faMessageSource.getMessage("FILE_NOT_DELETE_INTEGRATION", null, Locale.ENGLISH)); //file could not delete assign to some properties
            responseModel.setResult(fail);
            return responseModel;
        } catch (Exception e) {
            e.printStackTrace();
            responseModel.setError(e.toString());
            responseModel.setResult(fail);
            return responseModel;
        }

    }

    public ResponseModel copyFile(String uploadFolder, MultipartFile file, String title, HttpServletRequest request) {
        responseModel.clear();
        try {
            String extension = file.getContentType().substring(file.getContentType().indexOf("/") + 1, file.getContentType().length());
            if (extension.equalsIgnoreCase("mpg") && extension.equalsIgnoreCase("mp3"))
                throw new Exception(faMessageSource.getMessage("FILE_WRONG_FORMAT", null, Locale.ENGLISH));//فرمت فایل نادرست است
            responseModel.setContent(writeFile(file, uploadFolder, extension, title, request));
            responseModel.setResult(success);

        } catch (IOException e) {
            e.printStackTrace();
            responseModel.setResult(fail);
            responseModel.setSystemError(e.toString());
            responseModel.setError(faMessageSource.getMessage("FILE_UPLOAD_FAILED", null, Locale.ENGLISH));//خطا در بارگذاری فایل !
        } catch (EntityNotFoundException entityNotFoundException) {
            responseModel.setResult(fail);
            responseModel.setSystemError(entityNotFoundException.toString());
            responseModel.setError(entityNotFoundException.getMessage());
        } catch (Exception e) {
            responseModel.setResult(fail);
            responseModel.setSystemError(e.toString());
            responseModel.setError(faMessageSource.getMessage("FILE_UNKNOWN_ERROR", null, Locale.ENGLISH));// unknown error
        }
        return responseModel;
    }

    private File writeFile(MultipartFile file, String uploadFolder, String extension, String title, HttpServletRequest request) throws Exception {
        Long fileName = Long.valueOf(idGenerator());
        log.info("file type " + file.getContentType() + " extension " + extension);
        byte[] bytes;
        bytes = file.getBytes();
        Path path = Paths.get(uploadFolder + fileName + "." + extension);
        log.info("size of file in byte  " + bytes.length + " file address : " + path);
        java.nio.file.Files.write(path, bytes);
        return saveFileToDb(createFilesFromMultipart(title, fileName, extension, request));
    }

    private File createFilesFromMultipart(String title, Long fileName, String extension, HttpServletRequest request) throws Exception {
        File file = new File();
        file.setCreatedBy(((Person) securityService.getUserByToken(request).getContent()).getId());
        file.setCreateDate(new Date());
        file.setTitle(title);
        file.setUrl("/files/" + fileName + "." + extension);
        file.setName(fileName);
        file.setExtension(extension);
        return file;
    }

    private static class StreamGobbler implements Runnable {
        private InputStream inputStream;
        private Consumer<String> consumer;

        public StreamGobbler(InputStream inputStream, Consumer<String> consumer) {
            this.inputStream = inputStream;
            this.consumer = consumer;
        }

        @Override
        public void run() {
            new BufferedReader(new InputStreamReader(inputStream)).lines()
                    .forEach(consumer);
        }
    }

    private File saveFileToDb(File files) throws Exception {
        return fileRepository.save(files);
    }

    public File findById(Long fileId) {
        return fileRepository.findById(fileId).get();
    }

    public void remove(File file) {
        fileRepository.delete(file);
    }

    private String idGenerator() {
        return String.format("%06d", new Random().nextInt(1000000));
    }


}
