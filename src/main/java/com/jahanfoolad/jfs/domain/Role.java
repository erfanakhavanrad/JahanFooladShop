package com.jahanfoolad.jfs.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.sql.exec.spi.StandardEntityInstanceResolver;

import java.util.List;

@Entity
@Table(name = "Role")
@Getter
@Setter
public class Role extends AbstractEntity {

    @Column
    private String roleName;

    @OneToMany(cascade = {CascadeType.ALL})
    private List<Privilege> privilege;

}
