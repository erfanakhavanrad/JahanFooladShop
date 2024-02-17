package com.jahanfoolad.jfs.controller;

import com.jahanfoolad.jfs.domain.Privilege;
import com.jahanfoolad.jfs.domain.ResponseModel;
import com.jahanfoolad.jfs.domain.dto.PrivilegeDto;
import com.jahanfoolad.jfs.service.PrivilegeService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/privilege")
@RestController
public class PrivilegeController {
    @Autowired
    PrivilegeService privilegeService;

    @Autowired
    ResponseModel responseModel;

    @Value("${SUCCESS_RESULT}")
    int success;

    @Value("${FAIL_RESULT}")
    int fail;

    @GetMapping("/getall")
    public ResponseModel getPrivileges(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        responseModel.clear();
        try {
            List<Privilege> privileges = privilegeService.getPrivileges();
            responseModel.setContents(privileges);
            responseModel.setResult(success);
            responseModel.setRecordCount(privileges.size());
            responseModel.setStatus(httpServletResponse.getStatus());
        } catch (DataIntegrityViolationException dataIntegrityViolationException) {
            responseModel.setSystemError(dataIntegrityViolationException.getMessage());
            responseModel.setError(dataIntegrityViolationException.getMessage());
        } catch (Exception e) {
            responseModel.setError(e.getMessage());
        } finally {
            responseModel.setStatus(httpServletResponse.getStatus());
            responseModel.setResult(fail);
        }
        return responseModel;
    }


    @GetMapping(path = "/getbyid")
    public ResponseModel getPrivilegeById(@RequestParam Long id, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        try {
            responseModel.clear();
            responseModel.setContent(privilegeService.getPrivilegeById(id));
            responseModel.setResult(success);

        } catch (DataIntegrityViolationException dataIntegrityViolationException) {
            responseModel.setSystemError(dataIntegrityViolationException.getMessage());
            responseModel.setError(dataIntegrityViolationException.getMessage());
        } catch (Exception e) {
            responseModel.setError(e.getMessage());
        } finally {
            responseModel.setStatus(httpServletResponse.getStatus());
            responseModel.setResult(fail);
        }
        return responseModel;
    }


    @PostMapping("/save")
    public ResponseModel createPrivilege(@RequestBody PrivilegeDto privilegeDto, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {

        try {
            responseModel.clear();
            responseModel.setContent(privilegeService.createPrivilege(privilegeDto));
            responseModel.setResult(success);
        } catch (DataIntegrityViolationException dataIntegrityViolationException) {
            responseModel.setSystemError(dataIntegrityViolationException.getMessage());
            responseModel.setError(dataIntegrityViolationException.getMessage());
        } catch (Exception e) {
            responseModel.setError(e.getMessage());
        } finally {
            responseModel.setStatus(httpServletResponse.getStatus());
            responseModel.setResult(fail);
        }
        return responseModel;
    }


    @PutMapping(path = "/update")
    public ResponseModel updatePrivilege(@RequestBody PrivilegeDto privilegeDto, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        try {
            responseModel.clear();
            responseModel.setContent(privilegeService.updatePrivilege(privilegeDto));
            responseModel.setResult(success);
        } catch (DataIntegrityViolationException dataIntegrityViolationException) {
            responseModel.setSystemError(dataIntegrityViolationException.getMessage());
            responseModel.setError(dataIntegrityViolationException.getMessage());
        } catch (Exception e) {
            responseModel.setError(e.getMessage());
        } finally {
            responseModel.setStatus(httpServletResponse.getStatus());
            responseModel.setResult(fail);
        }
        return responseModel;
    }

    @DeleteMapping("/delete/{id}")
    public ResponseModel deletePrivilege(@PathVariable("id") Long id, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        responseModel.clear();
        try {
            privilegeService.deletePrivilege(id);
            responseModel.clear();
            responseModel.setResult(success);
        } catch (Exception e) {
            responseModel.setError(e.getMessage());
        } finally {
            responseModel.setStatus(httpServletResponse.getStatus());
            responseModel.setResult(fail);
        }
        return responseModel;
    }


}
