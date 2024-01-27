package com.jahanfoolad.jfs.controller;


import com.jahanfoolad.jfs.domain.CorpPerson;
import com.jahanfoolad.jfs.domain.ResponseModel;
import com.jahanfoolad.jfs.domain.Role;
import com.jahanfoolad.jfs.domain.dto.CorpPersonDto;
import com.jahanfoolad.jfs.domain.dto.RoleDto;
import com.jahanfoolad.jfs.service.RoleService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/role")
@RestController
public class RoleController {
    @Autowired
    RoleService roleService;

    @Autowired
    ResponseModel responseModel;


    @GetMapping("/getall")
    public ResponseModel getRoles(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        responseModel.clear();
        try {
            List<Role> roles = roleService.getRoles();
            responseModel.setContents(roles);
            responseModel.setResult(1);
            responseModel.setRecordCount(roles.size());
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
    public ResponseModel getRoleById(@RequestParam Long id, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        try {
            responseModel.clear();
            responseModel.setContent(roleService.getRoleByUserId(id));
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

    @PostMapping("/save")
    public ResponseModel createRole(@RequestBody RoleDto roleDto, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {

        try {
            responseModel.clear();
            responseModel.setContent(roleService.createRole(roleDto));
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
    public ResponseModel deleteRole(@PathVariable("id") Long id, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        responseModel.clear();
        try {
            roleService.deleteRole(id);
            responseModel.clear();
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

}
