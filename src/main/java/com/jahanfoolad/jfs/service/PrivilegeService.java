package com.jahanfoolad.jfs.service;

import com.jahanfoolad.jfs.domain.Privilege;
import com.jahanfoolad.jfs.domain.dto.PrivilegeDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;

import java.util.List;

public interface PrivilegeService  {
    Page<Privilege> getPrivileges(Integer pageNo, Integer perPage);
    Privilege getPrivilegeById(Long id) throws Exception;
    Privilege createPrivilege(PrivilegeDto privilegeDto, HttpServletRequest httpServletRequest) throws Exception;
    Privilege updatePrivilege(PrivilegeDto privilegeDto, HttpServletRequest httpServletRequest) throws Exception;
    void deletePrivilege(Long id);
}
