package com.jahanfoolad.jfs.controller;

import com.jahanfoolad.jfs.domain.Company;
import com.jahanfoolad.jfs.domain.ResponseModel;
import com.jahanfoolad.jfs.domain.dto.CompanyDto;
import com.jahanfoolad.jfs.domain.dto.ContactDto;
import com.jahanfoolad.jfs.service.CompanyService;
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
@RequestMapping("/company")
@RestController
public class CompanyController {

    @Autowired
    CompanyService companyService;

    @Autowired
    ResponseModel responseModel;

    @Resource(name = "faMessageSource")
    private MessageSource faMessageSource;

    @Value("${SUCCESS_RESULT}")
    int success;

    @Value("${FAIL_RESULT}")
    int fail;

    @GetMapping("/getAll")
    public ResponseModel getAll(@RequestParam Integer pageNo, Integer perPage,HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        try {
            responseModel.clear();
            log.info("get all company");
            Page<Company> companies = companyService.getCompany(pageNo,perPage);
            responseModel.setContent(companies);
            responseModel.setRecordCount((int) companies.getTotalElements());
            responseModel.setResult(success);
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

    @GetMapping("/getById")
    public ResponseModel getById(@RequestParam Long id, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        try {
            log.info("get company by id");
            responseModel.clear();
            responseModel.setContent(companyService.getCompanyById(id));
            responseModel.setRecordCount(1);
            responseModel.setResult(success);
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

    @PostMapping("/save")
    public ResponseModel save(@RequestBody CompanyDto companyDto, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {

        try {
            log.info("create company");
            responseModel.clear();
            responseModel.setContent(companyService.createCompany(companyDto,httpServletRequest));
            responseModel.setResult(success);
            responseModel.setStatus(httpServletResponse.getStatus());
        } catch (AccessDeniedException accessDeniedException) {
            responseModel.setError(faMessageSource.getMessage("ACCESS_DENIED", null, Locale.ENGLISH));
            responseModel.setResult(fail);
            responseModel.setSystemError(accessDeniedException.getMessage());
            responseModel.setStatus(HttpServletResponse.SC_FORBIDDEN);
        } catch (DataIntegrityViolationException dataIntegrityViolationException) {
            responseModel.setSystemError(dataIntegrityViolationException.getMessage());
            responseModel.setError(faMessageSource.getMessage("ALREADY_EXISTS", null, Locale.ENGLISH));
            responseModel.setResult(fail);
            responseModel.setStatus(httpServletResponse.getStatus());
        } catch (Exception e) {
            responseModel.setResult(fail);
            responseModel.setError(e.getMessage());
            responseModel.setStatus(httpServletResponse.getStatus());
        }
        return responseModel;

    }

    @DeleteMapping("/delete/{id}")
    public ResponseModel delete(@PathVariable("id") Long id, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        try {
            log.info("delete company");
            responseModel.clear();
            companyService.deleteCompany(id);
            responseModel.setResult(success);
            responseModel.setStatus(httpServletResponse.getStatus());
        } catch (Exception e) {
            responseModel.setError(e.getMessage());
            responseModel.setResult(fail);
            responseModel.setStatus(httpServletResponse.getStatus());
        }
        return responseModel;
    }

    @PutMapping("/update")
    public ResponseModel update(@RequestBody CompanyDto companyDto, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        try {
            log.info("update company");
            responseModel.setContent(companyService.updateCompany(companyDto,httpServletRequest));
            responseModel.setResult(success);
            responseModel.setStatus(httpServletResponse.getStatus());
        }catch (AccessDeniedException accessDeniedException) {
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

//    @GetMapping("/findByCategory")
//    public ResponseModel findByCategory(CategoryDto categoryDto, @RequestParam Long categoryId, Integer pageNo, Integer perPage, HttpServletResponse httpServletResponse, HttpServletRequest httpServletRequest) {
//        try {
//            responseModel.clear();
//            log.info("find by category");
//            Page<Category> category= companyService.findByCategory(categoryDto,pageNo,perPage);
//            responseModel.setContent(category);
//            responseModel.setResult(success);
//            responseModel.setRecordCount(category.size());
//            responseModel.setStatus(httpServletResponse.getStatus());
//        } catch (AccessDeniedException accessDeniedException) {
//            responseModel.setError(faMessageSource.getMessage("ACCESS_DENIED", null, Locale.ENGLISH));
//            responseModel.setResult(fail);
//            responseModel.setSystemError(accessDeniedException.getMessage());
//            responseModel.setStatus(HttpServletResponse.SC_FORBIDDEN);
//        } catch (Exception e) {
//            responseModel.setError(e.getMessage());
//            responseModel.setStatus(httpServletResponse.getStatus());
//            responseModel.setResult(fail);
//        }
//        return responseModel;
//    }

    @GetMapping("/findByProvince")
    public ResponseModel findByProvince(ContactDto contactDto, @RequestParam Integer pageNo, Integer perPage, HttpServletResponse httpServletResponse, HttpServletRequest httpServletRequest) {
        try {
            responseModel.clear();
            Page<Company> companies = companyService.findByProvince(contactDto, pageNo, perPage);
            responseModel.setContent(companies);
            responseModel.setResult(success);
            responseModel.setRecordCount((int) companies.getTotalElements());
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
            Page<Company> companies = companyService.findByCity(contactDto, pageNo, perPage);
            responseModel.setContent(companies);
            responseModel.setResult(success);
            responseModel.setRecordCount((int) companies.getTotalElements());
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

}
