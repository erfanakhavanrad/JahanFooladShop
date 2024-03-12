package com.jahanfoolad.jfs.jpaRepository;

import com.jahanfoolad.jfs.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findAllByRoleName(String roleName);
}
