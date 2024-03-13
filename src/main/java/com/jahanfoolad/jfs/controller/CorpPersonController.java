package com.jahanfoolad.jfs.controller;

import com.jahanfoolad.jfs.domain.CorpPerson;
import com.jahanfoolad.jfs.domain.ResponseModel;
import com.jahanfoolad.jfs.domain.dto.ContactDto;
import com.jahanfoolad.jfs.domain.dto.CorpPersonDto;
import com.jahanfoolad.jfs.service.CorpPersonService;
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

import org.springframework.security.access.AccessDeniedException;
import java.util.Locale;

@Slf4j
@RequestMapping("/corpperson")
@RestController
public class CorpPersonController {

    @Autowired
    CorpPersonService corpPersonService;

    @Autowired
    ResponseModel responseModel;

    @Resource(name = "faMessageSource")
    private MessageSource faMessageSource;

    @Value("${SUCCESS_RESULT}")
    int success;

    @Value("${FAIL_RESULT}")
    int fail;

    @PostMapping(path = "/login")
    public ResponseModel login(@RequestBody CorpPerson corpPerson, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        try {
            log.info("corp person login");
            responseModel.clear();
            CorpPerson user = corpPersonService.login(corpPerson, httpServletRequest);
            responseModel.setContent(user);
            responseModel.setResult(success);
            responseModel.setRecordCount(1);
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


    @GetMapping("/getAll")
    public ResponseModel getAll(@RequestParam Integer pageNo, Integer perPage, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        try {
            log.info("get all corp people");
            responseModel.clear();
            Page<CorpPerson> corpPeople = corpPersonService.getCorpPeople(pageNo, perPage);
            responseModel.setContents(corpPeople.getContent());
            responseModel.setResult(success);
            responseModel.setRecordCount((int) corpPeople.getTotalElements());
            responseModel.setStatus(httpServletResponse.getStatus());
        } catch (AccessDeniedException accessDeniedException) {
            responseModel.setError(faMessageSource.getMessage("ACCESS_DENIED", null, Locale.ENGLISH));
            responseModel.setResult(fail);
            responseModel.setSystemError(accessDeniedException.getMessage());
            responseModel.setStatus(HttpServletResponse.SC_FORBIDDEN);
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
            log.info("get corp person ById");
            responseModel.clear();
            responseModel.setContent(corpPersonService.getCorpPersonById(id));
            responseModel.setResult(success);
            responseModel.setRecordCount(1);
            responseModel.setStatus(httpServletResponse.getStatus());
        } catch (AccessDeniedException accessDeniedException) {
            responseModel.setError(faMessageSource.getMessage("ACCESS_DENIED", null, Locale.ENGLISH));
            responseModel.setResult(fail);
            responseModel.setSystemError(accessDeniedException.getMessage());
            responseModel.setStatus(HttpServletResponse.SC_FORBIDDEN);
        } catch (Exception e) {
            responseModel.setResult(fail);
            responseModel.setError(e.getMessage());
            responseModel.setStatus(httpServletResponse.getStatus());
        }
        return responseModel;
    }

    @GetMapping(path = "/findByContact")
    public ResponseModel findByContact(ContactDto contactDto, @RequestParam Integer pageNo, Integer perPage, HttpServletResponse httpServletResponse, HttpServletRequest httpServletRequest) {
        try {
            log.info("get corp people By contact");
            responseModel.clear();
            Page<CorpPerson> corpPeople = corpPersonService.findByContact(contactDto, pageNo, perPage);
            responseModel.setContents(corpPeople.getContent());
            responseModel.setResult(success);
            responseModel.setRecordCount((int) corpPeople.getTotalElements());
            responseModel.setStatus(httpServletResponse.getStatus());
        } catch (AccessDeniedException accessDeniedException) {
            responseModel.setError(faMessageSource.getMessage("ACCESS_DENIED", null, Locale.ENGLISH));
            responseModel.setResult(fail);
            responseModel.setSystemError(accessDeniedException.getMessage());
            responseModel.setStatus(HttpServletResponse.SC_FORBIDDEN);
        } catch (Exception e) {
            responseModel.setResult(fail);
            responseModel.setStatus(httpServletResponse.getStatus());
            responseModel.setError(e.getMessage());
        }
        return responseModel;
    }

    @GetMapping("/findByProvince")
    public ResponseModel findByProvince(ContactDto contactDto, @RequestParam Integer pageNo, Integer perPage, HttpServletResponse httpServletResponse, HttpServletRequest httpServletRequest) {
        try {
            responseModel.clear();
            Page<CorpPerson> corpPeople = corpPersonService.findByProvince(contactDto, pageNo, perPage);
            responseModel.setContents(corpPeople.getContent());
            responseModel.setResult(success);
            responseModel.setRecordCount((int) corpPeople.getTotalElements());
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
            Page<CorpPerson> corpPeople = corpPersonService.findByCity(contactDto, pageNo, perPage);
            responseModel.setContents(corpPeople.getContent());
            responseModel.setResult(success);
            responseModel.setRecordCount((int) corpPeople.getTotalElements());
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
    public ResponseModel save(@RequestBody CorpPersonDto corpPersonDto, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        try {
            log.info("save corp person");
            responseModel.clear();
            responseModel.setContent(corpPersonService.createCorpPerson(corpPersonDto, httpServletRequest));
            responseModel.setResult(success);
            responseModel.setStatus(httpServletResponse.getStatus());
        } catch (AccessDeniedException accessDeniedException) {
            responseModel.setError(faMessageSource.getMessage("ACCESS_DENIED", null, Locale.ENGLISH));
            responseModel.setResult(fail);
            responseModel.setStatus(HttpServletResponse.SC_FORBIDDEN);
            responseModel.setSystemError(accessDeniedException.getMessage());
        } catch (DataIntegrityViolationException dataIntegrityViolationException) {
            responseModel.setSystemError(dataIntegrityViolationException.getMessage());
            responseModel.setError(dataIntegrityViolationException.getMessage());
            responseModel.setResult(fail);
            responseModel.setStatus(HttpServletResponse.SC_FORBIDDEN);
        } catch (Exception e) {
            responseModel.setError(e.getMessage());
            responseModel.setResult(fail);
            responseModel.setStatus(httpServletResponse.getStatus());
        }
        return responseModel;
    }

    @PutMapping(path = "/update")
    public ResponseModel update(@RequestBody CorpPersonDto corpPersonDto, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        try {
            log.info("update corp person");
            responseModel.clear();
            responseModel.setContent(corpPersonService.updateCorpPerson(corpPersonDto, httpServletRequest));
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
            responseModel.setResult(fail);
            responseModel.setStatus(httpServletResponse.getStatus());
        } catch (Exception e) {
            responseModel.setError(e.getMessage());
            responseModel.setResult(fail);
            responseModel.setStatus(httpServletResponse.getStatus());
        }
        return responseModel;
    }

    @DeleteMapping("/delete/{id}")
    public ResponseModel delete(@PathVariable("id") Long id, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        try {
            log.info("delete corp person");
            responseModel.clear();
            corpPersonService.deleteCorpPerson(id);
            responseModel.setStatus(httpServletResponse.getStatus());
            responseModel.setResult(success);
        } catch (Exception e) {
            responseModel.setStatus(httpServletResponse.getStatus());
            responseModel.setResult(fail);
            responseModel.setError(e.getMessage());
        }
        return responseModel;
    }


}
