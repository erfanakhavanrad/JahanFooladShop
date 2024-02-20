package com.jahanfoolad.jfs.service;

import com.jahanfoolad.jfs.domain.Role;
import com.jahanfoolad.jfs.domain.dto.RoleDto;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface RoleService {
    List<Role> getRoles() throws Exception;

    Role getRoleById(Long id) throws Exception;

    Role createRole(RoleDto roleDto, HttpServletRequest httpServletRequest) throws Exception;

    Role updateRole(RoleDto roleDto, HttpServletRequest httpServletRequest) throws Exception;

    void deleteRole(Long id) throws Exception;

}
