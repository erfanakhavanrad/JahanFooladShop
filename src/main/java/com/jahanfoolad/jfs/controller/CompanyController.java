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
import java.util.List;
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
    public ResponseModel getAll(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {

        responseModel.clear();
        try {
            log.info("get company pesrson");
            List<Company> companies = companyService.getCompanyPersons();
            responseModel.setContent(companies);
            responseModel.setResult(success);
            responseModel.setRecordCount(companies.size());
            responseModel.setStatus(httpServletResponse.getStatus());
        } catch (DataIntegrityViolationException dataIntegrityViolationException) {
            responseModel.setSystemError(dataIntegrityViolationException.getMessage());
            responseModel.setError(faMessageSource.getMessage("already_not_exists", null, Locale.ENGLISH));
        }catch (AccessDeniedException accessDeniedException){
            responseModel.setError(faMessageSource.getMessage("ACCESS_DENIED", null, Locale.ENGLISH));
            responseModel.setResult(fail);
            responseModel.setSystemError(accessDeniedException.getMessage());
            responseModel.setStatus(HttpServletResponse.SC_FORBIDDEN);
        } catch (Exception e) {
            responseModel.setError(e.getMessage());
        } finally {
            responseModel.setStatus(httpServletResponse.getStatus());
            responseModel.setResult(fail);
        }
        return responseModel;
    }

    @GetMapping("/getById")
    public ResponseModel getCompanyByUserId(@RequestParam Long id, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {

        responseModel.clear();

        try {
            log.info("get company by user id");
            responseModel.setContent(companyService.getCompanyById(id));
            responseModel.setResult(success);
        } catch (Exception e) {
            responseModel.setError(e.getMessage());
        } finally {
            responseModel.setStatus(httpServletResponse.getStatus());
            responseModel.setResult(fail);
        }
        return responseModel;
    }

    @PostMapping("/save")
    public ResponseModel save(@RequestBody CompanyDto companyDto, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {

        try {
            log.info("create company");
            responseModel.clear();
            responseModel.setContent(companyService.createCompany(companyDto));
            responseModel.setResult(success);
        } catch (DataIntegrityViolationException dataIntegrityViolationException) {
            responseModel.setSystemError(dataIntegrityViolationException.getMessage());
            responseModel.setError(faMessageSource.getMessage("already_exists",null, Locale.ENGLISH));
        } catch (Exception e) {
            responseModel.setError(e.getMessage());
        } finally {
            responseModel.setStatus(httpServletResponse.getStatus());
            responseModel.setResult(fail);
        }
        return responseModel;

    }

    @DeleteMapping("/delete/{id}")
    public ResponseModel deleteCompany(@PathVariable("id") Long id, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        responseModel.clear();

        try {
            log.info("delete company");
            companyService.deleteCompany(id);
            responseModel.setResult(success);
        } catch (Exception e) {
            responseModel.setError(e.getMessage());
            responseModel.setResult(fail);
        } finally {
            responseModel.setStatus(httpServletResponse.getStatus());
            responseModel.setResult(fail);
        }
        return responseModel;
    }

    @PostMapping("/update")
    public ResponseModel updateCompany(@RequestBody CompanyDto companyDto , HttpServletRequest httpServletRequest , HttpServletResponse httpServletResponse){

        try{
            log.info("update company");
            responseModel.setContent(companyService.updateCompany(companyDto));
            responseModel.setResult(success);
        }catch (Exception e){
            responseModel.setError(e.getMessage());
        } finally {
            responseModel.setStatus(httpServletResponse.getStatus());
            responseModel.setResult(fail);
        }
        return  responseModel;
    }

    @GetMapping("/findByCategory")
    public ResponseModel findByCategory(@RequestParam Long categoryId , HttpServletResponse httpServletResponse , HttpServletRequest httpServletRequest){
        responseModel.clear();
        try{
            log.info("find by category");
            List<Company> companies = companyService.findByCategory(categoryId);
            responseModel.setContent(companies);
            responseModel.setResult(success);
            responseModel.setRecordCount(companies.size());
            responseModel.setStatus(httpServletResponse.getStatus());
         }catch (Exception e){
            responseModel.setError(e.getMessage());
        } finally {
            responseModel.setStatus(httpServletResponse.getStatus());
            responseModel.setResult(fail);
        }
        return  responseModel;
    }

    @GetMapping("/findByProvince")
    public ResponseModel findByProvince(ContactDto contactDto ,@RequestParam Integer pageNo ,Integer perPage , HttpServletResponse httpServletResponse , HttpServletRequest httpServletRequest){
        responseModel.clear();
        try{
            Page<Company> companies = companyService.findByProvince(contactDto,pageNo,perPage);
            responseModel.setContent(companies);
            responseModel.setResult(success);
            responseModel.setRecordCount((int) companies.getTotalElements());
            responseModel.setStatus(httpServletResponse.getStatus());
        }catch (DataIntegrityViolationException dataIntegrityViolationException){
            responseModel.setSystemError(dataIntegrityViolationException.getMessage());
            responseModel.setError(faMessageSource.getMessage("already_not_exists",null, Locale.ENGLISH));
        }catch (Exception e){
            responseModel.setError(e.getMessage());
        } finally {
            responseModel.setStatus(httpServletResponse.getStatus());
            responseModel.setResult(fail);
        }
        return  responseModel;
    }

    @GetMapping("/findByCity")
    public ResponseModel findByCity(ContactDto contactDto ,@RequestParam Integer pageNo ,Integer perPage , HttpServletResponse httpServletResponse , HttpServletRequest httpServletRequest){
        responseModel.clear();
        try{
            log.info("find by city");
            Page<Company> companies = companyService.findByCity(contactDto,pageNo,perPage);
            responseModel.setContent(companies);
            responseModel.setResult(success);
            responseModel.setRecordCount((int) companies.getTotalElements());
            responseModel.setStatus(httpServletResponse.getStatus());
        }catch (DataIntegrityViolationException dataIntegrityViolationException){
            responseModel.setSystemError(dataIntegrityViolationException.getMessage());
            responseModel.setError(faMessageSource.getMessage("already_not_exists",null, Locale.ENGLISH));
        }catch (Exception e){
            responseModel.setError(e.getMessage());
        } finally {
            responseModel.setStatus(httpServletResponse.getStatus());
            responseModel.setResult(fail);
        }
        return  responseModel;
    }

}
