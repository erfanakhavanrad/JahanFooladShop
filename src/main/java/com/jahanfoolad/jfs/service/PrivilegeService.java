package com.jahanfoolad.jfs.service;

import com.jahanfoolad.jfs.domain.Privilege;
import com.jahanfoolad.jfs.domain.dto.PrivilegeDto;

import java.util.List;

public interface PrivilegeService  {
    List<Privilege> getPrivileges();
    Privilege getPrivilegeById(Long id) throws Exception;
    Privilege createPrivilege(PrivilegeDto privilegeDto);
    Privilege updatePrivilege(PrivilegeDto privilegeDto) throws Exception;
    void deletePrivilege(Long id);
}
