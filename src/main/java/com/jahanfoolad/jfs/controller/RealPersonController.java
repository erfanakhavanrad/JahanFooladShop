package com.jahanfoolad.jfs.controller;

import com.jahanfoolad.jfs.domain.RealPerson;
import com.jahanfoolad.jfs.domain.ResponseModel;
import com.jahanfoolad.jfs.domain.dto.RealPersonDto;
import com.jahanfoolad.jfs.service.RealPersonService;
import com.jahanfoolad.jfs.service.SmsService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping(path = "/test")
    public String testMethod() {
        return "Hello.";
    }

    @GetMapping(path = "/forgetpassword")
    public ResponseModel forgetPassword(@RequestParam String userName, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {

        try {
            responseModel.clear();
            realPersonService.resetPass(userName);
        } catch (Exception e) {
            responseModel.setError(e.getMessage());
        } finally {
            responseModel.setStatus(httpServletResponse.getStatus());
            responseModel.setResult(0);
        }

        return responseModel;
    }

    @GetMapping(path = "/forgetpasswordconfirm")
    public ResponseModel forgetPasswordConfirm(@RequestParam String userName, @RequestParam String newPassword, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {

        responseModel.clear();
        try {
            realPersonService.resetPassConfirm(userName, newPassword);
            RealPerson user = realPersonService.getRealPersonByUsername(userName);
            responseModel.setContent(user);
            responseModel.setResult(1);
            responseModel.setRecordCount(1);
            responseModel.setStatus(httpServletResponse.getStatus());
        } catch (DataIntegrityViolationException dataIntegrityViolationException) {
            responseModel.setError(dataIntegrityViolationException.getMessage());
        } catch (Exception e) {
            responseModel.setError(e.getMessage());
        } finally {
            responseModel.setStatus(httpServletResponse.getStatus());
            responseModel.setResult(0);
        }


        return responseModel;
    }

    @GetMapping(path = "/login")
    public ResponseModel login(@RequestBody RealPerson realPerson, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {

        responseModel.clear();
        try {
            RealPerson user = realPersonService.login(realPerson);
            responseModel.setContent(user);
            responseModel.setResult(1);
            responseModel.setRecordCount(1);
            responseModel.setStatus(httpServletResponse.getStatus());
        } catch (DataIntegrityViolationException dataIntegrityViolationException) {
            responseModel.setError(dataIntegrityViolationException.getMessage());
        } catch (Exception e) {
            responseModel.setError(e.getMessage());
        } finally {
            responseModel.setStatus(httpServletResponse.getStatus());
            responseModel.setResult(0);
        }
        return responseModel;

    }

    @GetMapping("/getall")
    public ResponseModel getRealPeople(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        responseModel.clear();
        try {
            log.info("GET ALL USERS");
            List<RealPerson> users = realPersonService.getRealPersons();
            responseModel.setContents(users);
            responseModel.setResult(1);
            responseModel.setRecordCount(users.size());
            responseModel.setStatus(httpServletResponse.getStatus());
        } catch (DataIntegrityViolationException dataIntegrityViolationException) {
            responseModel.setError(dataIntegrityViolationException.getMessage());
        } catch (Exception e) {
            responseModel.setError(e.getMessage());
        } finally {
            responseModel.setStatus(httpServletResponse.getStatus());
            responseModel.setResult(0);
        }
        return responseModel;
    }

    @GetMapping(path = "/getbyid")
//    public String getUserById(@PathVariable Long userid) {
    public ResponseModel getRealPersonById(@RequestParam Long id, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
//        , HttpServletRequest httpServletRequest , HttpServletResponse httpServletResponse
        try {

            log.info("-------------");

            responseModel.clear();
            responseModel.setContent(realPersonService.getRealPersonByUserId(id));
            responseModel.setResult(1);
//            responseModel.set
//        return userService.getUserByUserId(userid);
//        } catch (AccessDeniedException accessDeniedException) {
//            responseModel.setResult(0);
//            responseModel.setSystemError(accessDeniedException.getMessage());
//            responseModel.setError("properties");

//            httpServletResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
        } catch (DataIntegrityViolationException dataIntegrityViolationException) {
            responseModel.setError(dataIntegrityViolationException.getMessage());
        } catch (Exception e) {
            responseModel.setError(e.getMessage());
        } finally {
            responseModel.setStatus(httpServletResponse.getStatus());
            responseModel.setResult(0);
        }
        return responseModel;
    }

    @PostMapping("/save")
    public ResponseModel createRealPerson(@RequestBody RealPersonDto realPersonDto, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {

        try {
            responseModel.clear();
            responseModel.setContent(realPersonService.createRealPerson(realPersonDto));
            responseModel.setResult(1);
        } catch (DataIntegrityViolationException dataIntegrityViolationException) {
            responseModel.setError(dataIntegrityViolationException.getMessage());
        } catch (Exception e) {
            responseModel.setError(e.getMessage());
        } finally {
            responseModel.setStatus(httpServletResponse.getStatus());
            responseModel.setResult(0);
        }
        return responseModel;
    }

    @DeleteMapping("/delete/{id}")
    public ResponseModel deleteRealPerson(@PathVariable("id") Long id, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        responseModel.clear();
        try {
            log.info("DELETE USER");
            realPersonService.deleteRealPerson(id);
            responseModel.clear();
            responseModel.setResult(1);
        } catch (Exception e) {
            responseModel.setError(e.getMessage());
        } finally {
            responseModel.setStatus(httpServletResponse.getStatus());
            responseModel.setResult(0);
        }
        return responseModel;
    }


}
