package com.jahanfoolad.jfs.controller;

import com.jahanfoolad.jfs.domain.RealPerson;
import com.jahanfoolad.jfs.domain.ResponseModel;
import com.jahanfoolad.jfs.domain.dto.ContactDto;
import com.jahanfoolad.jfs.domain.dto.RealPersonDto;
import com.jahanfoolad.jfs.service.RealPersonService;
import com.jahanfoolad.jfs.service.SmsService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.Locale;

@Slf4j
@RequestMapping("/realperson")
@RestController
public class RealPersonController {

    @Autowired
    RealPersonService realPersonService;

    @Autowired
    ResponseModel responseModel;

    @Autowired
    SmsService smsService;

    @Value("${SUCCESS_RESULT}")
    int success;

    @Value("${FAIL_RESULT}")
    int fail;

    @Resource(name = "faMessageSource")
    private MessageSource faMessageSource;

    @GetMapping(path = "/test")
    public String testMethod() {
        return "Hello.";
    }

    @GetMapping(path = "/forgetPassword")
    public ResponseModel forgetPassword(@RequestParam String userName, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {

        try {
            log.info("forget password");
            responseModel.clear();
            realPersonService.resetPass(userName);
            responseModel.setResult(success);
            responseModel.setStatus(httpServletResponse.getStatus());
        } catch (AccessDeniedException accessDeniedException) {
            responseModel.setError(faMessageSource.getMessage("ACCESS_DENIED", null, Locale.ENGLISH));
            responseModel.setResult(fail);
            responseModel.setSystemError(accessDeniedException.getMessage());
            responseModel.setStatus(HttpServletResponse.SC_FORBIDDEN);
        } catch (Exception e) {
            responseModel.setError(e.getMessage());
            responseModel.setStatus(httpServletResponse.getStatus());
            responseModel.setResult(fail);
        }
        return responseModel;
    }

    @GetMapping(path = "/forgetPasswordConfirm")
    public ResponseModel forgetPasswordConfirm(@RequestParam String userName, @RequestParam String newPassword, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {

        try {
            log.info("forget password confirm");
            responseModel.clear();
            realPersonService.resetPassConfirm(userName, newPassword);
            RealPerson user = realPersonService.getRealPersonByUsername(userName);
            responseModel.setContent(user);
            responseModel.setResult(success);
            responseModel.setRecordCount(1);
            responseModel.setStatus(httpServletResponse.getStatus());
        } catch (AccessDeniedException accessDeniedException) {
            responseModel.setError(faMessageSource.getMessage("ACCESS_DENIED", null, Locale.ENGLISH));
            responseModel.setResult(fail);
            responseModel.setSystemError(accessDeniedException.getMessage());
            responseModel.setStatus(HttpServletResponse.SC_FORBIDDEN);
        } catch (DataIntegrityViolationException dataIntegrityViolationException) {
            responseModel.setSystemError(dataIntegrityViolationException.getMessage());
            responseModel.setStatus(httpServletResponse.getStatus());
            responseModel.setResult(fail);
            responseModel.setError(dataIntegrityViolationException.getMessage());
        } catch (Exception e) {
            responseModel.setResult(fail);
            responseModel.setStatus(httpServletResponse.getStatus());
            responseModel.setError(e.getMessage());
        }
        return responseModel;
    }

    @PostMapping(path = "/login")
    public ResponseModel login(@RequestBody RealPerson realPerson, HttpServletRequest request, HttpServletResponse httpServletResponse) {
        responseModel.clear();
        return realPersonService.login(realPerson, request);
    }

    @GetMapping("/getAll")
    public ResponseModel getAll(@RequestParam Integer pageNo, Integer perPage, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        try {
            responseModel.clear();
            log.info("GET ALL USERS");
            Page<RealPerson> users = realPersonService.getRealPeople(pageNo, perPage);
            responseModel.setContents(users.getContent());
            responseModel.setResult(success);
            responseModel.setRecordCount((int) users.getTotalElements());
            responseModel.setStatus(httpServletResponse.getStatus());
        } catch (AccessDeniedException accessDeniedException) {
            responseModel.setError(faMessageSource.getMessage("ACCESS_DENIED", null, Locale.ENGLISH));
            responseModel.setResult(fail);
            responseModel.setSystemError(accessDeniedException.getMessage());
            responseModel.setStatus(HttpServletResponse.SC_FORBIDDEN);
        } catch (DataIntegrityViolationException dataIntegrityViolationException) {
            responseModel.setSystemError(dataIntegrityViolationException.getMessage());
            responseModel.setError(dataIntegrityViolationException.getMessage());
            responseModel.setStatus(httpServletResponse.getStatus());
            responseModel.setResult(fail);
        } catch (Exception e) {
            responseModel.setStatus(httpServletResponse.getStatus());
            responseModel.setResult(fail);
            responseModel.setError(e.getMessage());
        }
        return responseModel;
    }

    @GetMapping(path = "/getById")
    public ResponseModel getById(@RequestParam Long id, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        try {
            log.info("getRealPersonById");
            responseModel.clear();
            responseModel.setContent(realPersonService.getRealPersonById(id));
            responseModel.setResult(success);
            responseModel.setStatus(httpServletResponse.getStatus());
        } catch (AccessDeniedException accessDeniedException) {
            responseModel.setError(faMessageSource.getMessage("ACCESS_DENIED", null, Locale.ENGLISH));
            responseModel.setResult(fail);
            responseModel.setSystemError(accessDeniedException.getMessage());
            responseModel.setStatus(HttpServletResponse.SC_FORBIDDEN);
        } catch (DataIntegrityViolationException dataIntegrityViolationException) {
            responseModel.setSystemError(dataIntegrityViolationException.getMessage());
            responseModel.setError(dataIntegrityViolationException.getMessage());
            responseModel.setStatus(httpServletResponse.getStatus());
            responseModel.setResult(fail);
        } catch (Exception e) {
            responseModel.setError(e.getMessage());
            responseModel.setStatus(httpServletResponse.getStatus());
            responseModel.setResult(fail);
        }
        return responseModel;
    }

    @GetMapping("/findByProvince")
    public ResponseModel findByProvince(ContactDto contactDto, @RequestParam Integer pageNo, Integer perPage, HttpServletResponse httpServletResponse, HttpServletRequest httpServletRequest) {
        try {
            responseModel.clear();
            Page<RealPerson> realPeople = realPersonService.findByProvince(contactDto, pageNo, perPage);
            responseModel.setContents(realPeople.getContent());
            responseModel.setResult(success);
            responseModel.setRecordCount((int) realPeople.getTotalElements());
            responseModel.setStatus(httpServletResponse.getStatus());
        } catch (AccessDeniedException accessDeniedException) {
            responseModel.setError(faMessageSource.getMessage("ACCESS_DENIED", null, Locale.ENGLISH));
            responseModel.setResult(fail);
            responseModel.setSystemError(accessDeniedException.getMessage());
            responseModel.setStatus(HttpServletResponse.SC_FORBIDDEN);
        } catch (Exception e) {
            responseModel.setError(e.getMessage());
            responseModel.setResult(fail);
            responseModel.setStatus(httpServletResponse.getStatus());
        }
        return responseModel;
    }

    @GetMapping("/findByCity")
    public ResponseModel findByCity(ContactDto contactDto, @RequestParam Integer pageNo, Integer perPage, HttpServletResponse httpServletResponse, HttpServletRequest httpServletRequest) {
        responseModel.clear();
        try {
            log.info("find by city");
            Page<RealPerson> realPeople = realPersonService.findByCity(contactDto, pageNo, perPage);
            responseModel.setContents(realPeople.getContent());
            responseModel.setResult(success);
            responseModel.setRecordCount((int) realPeople.getTotalElements());
            responseModel.setStatus(httpServletResponse.getStatus());
        } catch (DataIntegrityViolationException dataIntegrityViolationException) {
            responseModel.setSystemError(dataIntegrityViolationException.getMessage());
            responseModel.setError(faMessageSource.getMessage("ALREADY_NOT_EXISTS", null, Locale.ENGLISH));
            responseModel.setResult(fail);
            responseModel.setStatus(httpServletResponse.getStatus());
        } catch (Exception e) {
            responseModel.setError(e.getMessage());
            responseModel.setResult(fail);
            responseModel.setStatus(httpServletResponse.getStatus());
        }
        return responseModel;
    }

    @PostMapping("/save")
    public ResponseModel save(@RequestBody RealPersonDto realPersonDto, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {

        try {
            log.info("save RealPerson");
            responseModel.clear();
            responseModel.setContent(realPersonService.createRealPerson(realPersonDto, httpServletRequest));
            responseModel.setResult(success);
            responseModel.setStatus(httpServletResponse.getStatus());
        } catch (AccessDeniedException accessDeniedException) {
            responseModel.setError(faMessageSource.getMessage("ACCESS_DENIED", null, Locale.ENGLISH));
            responseModel.setResult(fail);
            responseModel.setSystemError(accessDeniedException.getMessage());
            responseModel.setStatus(HttpServletResponse.SC_FORBIDDEN);
        } catch (DataIntegrityViolationException dataIntegrityViolationException) {
            responseModel.setStatus(httpServletResponse.getStatus());
            responseModel.setResult(fail);
            responseModel.setSystemError(dataIntegrityViolationException.getMessage());
            responseModel.setError(faMessageSource.getMessage("USER_ALREADY_EXISTS", null, Locale.ENGLISH));
        } catch (Exception e) {
            responseModel.setResult(fail);
            responseModel.setStatus(httpServletResponse.getStatus());
            responseModel.setError(e.getMessage());
        }
        return responseModel;
    }

    @PutMapping(path = "/update")
    public ResponseModel update(@RequestBody RealPersonDto realPersonDto, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        try {
            log.info("update RealPerson");
            responseModel.clear();
            responseModel.setContent(realPersonService.updateRealPerson(realPersonDto, httpServletRequest));
            responseModel.setResult(success);
            responseModel.setStatus(httpServletResponse.getStatus());
        } catch (AccessDeniedException accessDeniedException) {
            responseModel.setError(faMessageSource.getMessage("ACCESS_DENIED", null, Locale.ENGLISH));
            responseModel.setResult(fail);
            responseModel.setSystemError(accessDeniedException.getMessage());
            responseModel.setStatus(HttpServletResponse.SC_FORBIDDEN);
        } catch (DataIntegrityViolationException dataIntegrityViolationException) {
            responseModel.setSystemError(dataIntegrityViolationException.getMessage());
            responseModel.setStatus(httpServletResponse.getStatus());
            responseModel.setResult(fail);
            responseModel.setError(dataIntegrityViolationException.getMessage());
        } catch (Exception e) {
            responseModel.setResult(fail);
            responseModel.setStatus(httpServletResponse.getStatus());
            responseModel.setError(e.getMessage());
        }
        return responseModel;
    }

    @DeleteMapping("/delete/{id}")
    public ResponseModel delete(@PathVariable("id") Long id, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        responseModel.clear();
        try {
            log.info("DELETE RealPerson");
            realPersonService.deleteRealPerson(id);
            responseModel.clear();
            responseModel.setResult(success);
            responseModel.setStatus(httpServletResponse.getStatus());
        } catch (AccessDeniedException accessDeniedException) {
            responseModel.setError(faMessageSource.getMessage("ACCESS_DENIED", null, Locale.ENGLISH));
            responseModel.setResult(fail);
            responseModel.setSystemError(accessDeniedException.getMessage());
            responseModel.setStatus(HttpServletResponse.SC_FORBIDDEN);
        } catch (Exception e) {
            responseModel.setError(e.getMessage());
            responseModel.setStatus(httpServletResponse.getStatus());
            responseModel.setResult(fail);
        }
        return responseModel;
    }

}
