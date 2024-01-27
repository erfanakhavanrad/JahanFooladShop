package com.jahanfoolad.jfs.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.sql.exec.spi.StandardEntityInstanceResolver;

@Entity
@Table(name = "Role")
@Getter
@Setter
public class Role extends AbstractEntity {
    private Privilege privilege;
    private String roleName;
}
