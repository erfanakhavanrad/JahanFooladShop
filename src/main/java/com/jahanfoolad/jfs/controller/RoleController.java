package com.jahanfoolad.jfs.controller;


import com.jahanfoolad.jfs.domain.ResponseModel;
import com.jahanfoolad.jfs.domain.Role;
import com.jahanfoolad.jfs.domain.dto.RoleDto;
import com.jahanfoolad.jfs.service.RoleService;
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
@RequestMapping("/role")
@RestController
public class RoleController {

    @Autowired
    RoleService roleService;

    @Autowired
    ResponseModel responseModel;

    @Resource(name = "faMessageSource")
    private MessageSource faMessageSource;

    @Value("${SUCCESS_RESULT}")
    int success;

    @Value("${FAIL_RESULT}")
    int fail;

    @GetMapping("/getAll")
    public ResponseModel getAll(@RequestParam Integer pageNo, Integer perPage, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        try {
            log.info("Get all Roles");
            responseModel.clear();
            Page<Role> roles = roleService.getRoles(pageNo, perPage);
            responseModel.setContents(roles.getContent());
            responseModel.setResult(success);
            responseModel.setRecordCount((int) roles.getTotalElements());
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
            log.info("Get Role ById");
            responseModel.clear();
            responseModel.setContent(roleService.getRoleById(id));
            responseModel.setResult(success);
            responseModel.setRecordCount(1);
            responseModel.setStatus(httpServletResponse.getStatus());
        } catch (AccessDeniedException accessDeniedException) {
            responseModel.setError(faMessageSource.getMessage("ACCESS_DENIED", null, Locale.ENGLISH));
            responseModel.setResult(fail);
            responseModel.setSystemError(accessDeniedException.getMessage());
            responseModel.setStatus(HttpServletResponse.SC_FORBIDDEN);
        } catch (DataIntegrityViolationException dataIntegrityViolationException) {
            responseModel.setResult(fail);
            responseModel.setSystemError(dataIntegrityViolationException.getMessage());
            responseModel.setError(dataIntegrityViolationException.getMessage());
            responseModel.setStatus(httpServletResponse.getStatus());
        } catch (Exception e) {
            responseModel.setResult(fail);
            responseModel.setError(e.getMessage());
            responseModel.setStatus(httpServletResponse.getStatus());
        }
        return responseModel;
    }

    @PostMapping("/save")
    public ResponseModel save(@RequestBody RoleDto roleDto, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        try {
            log.info("Save Role");
            responseModel.clear();
            final Role role = roleService.createRole(roleDto, httpServletRequest);
            responseModel.setContent(role);
            responseModel.setResult(success);
            responseModel.setStatus(httpServletResponse.getStatus());
        } catch (AccessDeniedException accessDeniedException) {
            responseModel.setError(faMessageSource.getMessage("ACCESS_DENIED", null, Locale.ENGLISH));
            responseModel.setResult(fail);
            responseModel.setSystemError(accessDeniedException.getMessage());
            responseModel.setStatus(HttpServletResponse.SC_FORBIDDEN);
        } catch (DataIntegrityViolationException dataIntegrityViolationException) {
            responseModel.setContents(null);
            responseModel.setContent(null);
            responseModel.setSystemError(dataIntegrityViolationException.getMessage());
            responseModel.setStatus(httpServletResponse.getStatus());
            responseModel.setResult(fail);
            responseModel.setError(dataIntegrityViolationException.getMessage());
        } catch (Exception e) {
            responseModel.setResult(fail);
            responseModel.setError(e.getMessage());
            responseModel.setStatus(httpServletResponse.getStatus());
        }
        return responseModel;
    }

    @PutMapping(path = "/update")
    public ResponseModel update(@RequestBody RoleDto roleDto, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        try {
            log.info("update Role");
            responseModel.clear();
            responseModel.setContent(roleService.updateRole(roleDto, httpServletRequest));
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
            responseModel.setResult(fail);
            responseModel.setStatus(httpServletResponse.getStatus());
            responseModel.setError(e.getMessage());
        }
        return responseModel;
    }

    @DeleteMapping("/delete/{id}")
    public ResponseModel delete(@PathVariable("id") Long id, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        try {
            log.info("delete Role");
            responseModel.clear();
            roleService.deleteRole(id);
            responseModel.setStatus(httpServletResponse.getStatus());
            responseModel.clear();
            responseModel.setResult(success);
        } catch (AccessDeniedException accessDeniedException) {
            responseModel.setError(faMessageSource.getMessage("ACCESS_DENIED", null, Locale.ENGLISH));
            responseModel.setResult(fail);
            responseModel.setSystemError(accessDeniedException.getMessage());
            responseModel.setStatus(HttpServletResponse.SC_FORBIDDEN);
        } catch (Exception e) {
            responseModel.setStatus(httpServletResponse.getStatus());
            responseModel.setResult(fail);
            responseModel.setError(e.getMessage());
        } finally {
            responseModel.setStatus(httpServletResponse.getStatus());
            responseModel.setResult(fail);
        }
        return responseModel;
    }

}
