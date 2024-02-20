package com.jahanfoolad.jfs.service;

import com.jahanfoolad.jfs.domain.Privilege;
import com.jahanfoolad.jfs.domain.dto.PrivilegeDto;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface PrivilegeService  {
    List<Privilege> getPrivileges();
    Privilege getPrivilegeById(Long id) throws Exception;
    Privilege createPrivilege(PrivilegeDto privilegeDto, HttpServletRequest httpServletRequest) throws Exception;
    Privilege updatePrivilege(PrivilegeDto privilegeDto, HttpServletRequest httpServletRequest) throws Exception;
    void deletePrivilege(Long id);
}
