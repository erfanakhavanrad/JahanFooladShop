package com.jahanfoolad.jfs.service.impl;

import com.jahanfoolad.jfs.domain.Category;
import com.jahanfoolad.jfs.domain.ResponseModel;
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

    @Autowired
    ResponseModel responseModel;

    @Override
    public List<Role> getRoles() {
        return roleRepository.findAll();
    }

    @Override
    public Role getRoleById(Long id) throws Exception {
        return roleRepository.findById(id).orElseThrow(() -> new Exception(faMessageSource.getMessage("NOT_FOUND", null, Locale.ENGLISH)));
    }

    @Override
    public Role createRole(RoleDto roleDto) {
        ModelMapper modelMapper = new ModelMapper();
        Role role = modelMapper.map(roleDto, Role.class);
        return modelMapper.map(roleRepository.save(role), Role.class);
    }

    @Override
    public Role updateRole(RoleDto roleDto) throws Exception {
        ModelMapper modelMapper = new ModelMapper();

        Role oldRole = getRoleById(roleDto.getId());
        Role newRole = modelMapper.map(roleDto, Role.class);

        responseModel.clear();
        Role updated = (Role) responseModel.merge(oldRole, newRole);

        if (roleDto.getPrivilege() != null && !roleDto.getPrivilege().isEmpty()) {
            updated.setPrivileges(newRole.getPrivileges());
        }

        return roleRepository.save(updated);
    }

    @Override
    public void deleteRole(Long id) {
        roleRepository.deleteById(id);
    }
}
