package com.jahanfoolad.jfs.service.impl;

import com.jahanfoolad.jfs.domain.Role;
import com.jahanfoolad.jfs.domain.dto.RoleDto;
import com.jahanfoolad.jfs.jpaRepository.RoleRepository;
import com.jahanfoolad.jfs.service.RoleService;
import jakarta.annotation.Resource;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    RoleRepository roleRepository;

    @Resource(name = "faMessageSource")
    private MessageSource faMessageSource;
    @Resource(name = "enMessageSource")
    private MessageSource enMessageSource;

    @Override
    public List<Role> getRoles() {
        return roleRepository.findAll();
    }

    @Override
    public Role getRoleByUserId(Long id) throws Exception {
        return roleRepository.findById(id).orElseThrow(() -> new Exception(enMessageSource.getMessage("item_not_found_message",null, Locale.ENGLISH)));
    }

    @Override
    public Role createRole(RoleDto roleDto) {
        ModelMapper modelMapper = new ModelMapper();
        Role role = modelMapper.map(roleDto, Role.class);
        return modelMapper.map(roleRepository.save(role), Role.class);
    }

    @Override
    public void deleteRole(Long id) {
        roleRepository.deleteById(id);
    }
}
