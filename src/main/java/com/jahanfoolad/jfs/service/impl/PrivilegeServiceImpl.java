package com.jahanfoolad.jfs.service.impl;

import com.jahanfoolad.jfs.JfsApplication;
import com.jahanfoolad.jfs.domain.Person;
import com.jahanfoolad.jfs.domain.Privilege;
import com.jahanfoolad.jfs.domain.ResponseModel;
import com.jahanfoolad.jfs.domain.dto.PrivilegeDto;
import com.jahanfoolad.jfs.jpaRepository.PrivilegeRepository;
import com.jahanfoolad.jfs.security.SecurityService;
import com.jahanfoolad.jfs.service.PrivilegeService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class PrivilegeServiceImpl implements PrivilegeService {

    @Autowired
    PrivilegeRepository privilegeRepository;

    @Resource(name = "faMessageSource")
    private MessageSource faMessageSource;

    @Autowired
    ResponseModel responseModel;

    @Autowired
    private SecurityService securityService;

    @Override
    public Page<Privilege> getPrivileges(Integer pageNo, Integer perPage) {
        return privilegeRepository.findAll(JfsApplication.createPagination(pageNo, perPage));
    }

    @Override
    public Privilege getPrivilegeById(Long id) throws Exception {
        return privilegeRepository.findById(id).orElseThrow(() -> new Exception(faMessageSource.getMessage("PRIVILEGE_NOT_FOUND", null, Locale.ENGLISH)));
    }

    @Override
    public Privilege createPrivilege(PrivilegeDto privilegeDto, HttpServletRequest httpServletRequest) {
        ModelMapper modelMapper = new ModelMapper();
        Privilege privilege = modelMapper.map(privilegeDto, Privilege.class);
        privilege.setCreatedBy((((Person) securityService.getUserByToken(httpServletRequest).getContent()).getId()));
        return privilegeRepository.save(privilege);
    }

    @Override
    public Privilege updatePrivilege(PrivilegeDto privilegeDto, HttpServletRequest httpServletRequest) throws Exception {
        ModelMapper modelMapper = new ModelMapper();

        Privilege oldPrivilege = getPrivilegeById(privilegeDto.getId());
        Privilege newPrivilege = modelMapper.map(privilegeDto, Privilege.class);

        responseModel.clear();
        Privilege updated = (Privilege) responseModel.merge(oldPrivilege, newPrivilege);
        return privilegeRepository.save(updated);
    }

    @Override
    public void deletePrivilege(Long id) {
        privilegeRepository.deleteById(id);
    }
}
