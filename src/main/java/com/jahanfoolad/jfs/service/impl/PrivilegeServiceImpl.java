package com.jahanfoolad.jfs.service.impl;

import com.jahanfoolad.jfs.domain.Privilege;
import com.jahanfoolad.jfs.domain.ResponseModel;
import com.jahanfoolad.jfs.domain.dto.PrivilegeDto;
import com.jahanfoolad.jfs.jpaRepository.PrivilegeRepository;
import com.jahanfoolad.jfs.service.PrivilegeService;
import jakarta.annotation.Resource;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;

@Service
public class PrivilegeServiceImpl implements PrivilegeService {

    @Autowired
    PrivilegeRepository privilegeRepository;

    @Resource(name = "faMessageSource")
    private MessageSource faMessageSource;
    @Resource(name = "enMessageSource")
    private MessageSource enMessageSource;

    @Autowired
    ResponseModel responseModel;

    @Override
    public List<Privilege> getPrivileges() {
        return privilegeRepository.findAll();
    }

    @Override
    public Privilege getPrivilegeById(Long id) throws Exception {
        return privilegeRepository.findById(id).orElseThrow(() -> new Exception(faMessageSource.getMessage("NOT_FOUND", null, Locale.ENGLISH)));
    }

    @Override
    public Privilege createPrivilege(PrivilegeDto privilegeDto) {
        ModelMapper modelMapper = new ModelMapper();
        Privilege privilege = modelMapper.map(privilegeDto, Privilege.class);
        return modelMapper.map(privilegeRepository.save(privilege), Privilege.class);
    }

    @Override
    public Privilege updatePrivilege(PrivilegeDto privilegeDto) throws Exception {
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
