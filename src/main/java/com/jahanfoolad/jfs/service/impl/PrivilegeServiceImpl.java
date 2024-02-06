package com.jahanfoolad.jfs.service.impl;

import com.jahanfoolad.jfs.domain.Privilege;
import com.jahanfoolad.jfs.domain.dto.PrivilegeDto;
import com.jahanfoolad.jfs.jpaRepository.PrivilegeRepository;
import com.jahanfoolad.jfs.service.PrivilegeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PrivilegeServiceImpl implements PrivilegeService {

    @Autowired
    PrivilegeRepository privilegeRepository;

    @Override
    public List<Privilege> getPrivileges() {
        return privilegeRepository.findAll();
    }

    @Override
    public Privilege getPrivilegeByUserId(Long id) throws Exception {
        return privilegeRepository.findById(id).orElseThrow(() -> new Exception("Privilege Not Found"));
    }

    @Override
    public Privilege createPrivilege(PrivilegeDto privilegeDto) {
        ModelMapper modelMapper = new ModelMapper();
        Privilege privilege = modelMapper.map(privilegeDto, Privilege.class);
        return modelMapper.map(privilegeRepository.save(privilege), Privilege.class);
    }

    @Override
    public void deletePrivilege(Long id) {
        privilegeRepository.deleteById(id);
    }
}
