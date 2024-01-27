package com.jahanfoolad.jfs.service;

import com.jahanfoolad.jfs.domain.Privilege;
import com.jahanfoolad.jfs.domain.dto.PrivilegeDto;

import java.util.List;

public interface PrivilegeService  {
    List<Privilege> getPrivileges();
    Privilege getPrivilegeByUserId(Long id) throws Exception;
    Privilege createPrivilege(PrivilegeDto privilegeDto);
    void deletePrivilege(Long id);
}
