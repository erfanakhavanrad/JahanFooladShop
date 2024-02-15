package com.jahanfoolad.jfs.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.sql.exec.spi.StandardEntityInstanceResolver;

import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "Role")
public class Role extends AbstractEntity {

    @Column(nullable = false)
    private String roleName;


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "role_privileges",
            joinColumns =
            @JoinColumn(name = "role_id", referencedColumnName = "id"),
            inverseJoinColumns =
            @JoinColumn(name = "privilege_id", referencedColumnName = "id"))
    private List<Privilege> privileges;
}
