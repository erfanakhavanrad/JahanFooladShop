package com.jahanfoolad.jfs.service;

import com.jahanfoolad.jfs.domain.Role;
import com.jahanfoolad.jfs.domain.dto.RoleDto;

import java.util.List;

public interface RoleService {
    List<Role> getRoles();

    Role getRoleById(Long id) throws Exception;

    Role createRole(RoleDto roleDto);
    Role updateRole(RoleDto roleDto) throws Exception;

    void deleteRole(Long id);

}
