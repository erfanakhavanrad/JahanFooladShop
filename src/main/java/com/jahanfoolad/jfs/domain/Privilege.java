package com.jahanfoolad.jfs.domain;

import com.jahanfoolad.jfs.domain.AbstractEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;



@Setter
@Getter
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"domain", "access"})})
@SequenceGenerator(initialValue = 20 ,name = "company_seq" , allocationSize =1)
public class Privilege extends AbstractEntity implements GrantedAuthority {

    public Privilege() {
    }

    public Privilege(String access, String domain) {
        this.access = access;
        this.domain = domain;
    }

    @Column(nullable = false)
    private String access;

    @Column(nullable = false)
    private String domain;

    @Column(nullable = false)
    private String name;

    @Override
    public String getAuthority() {
        return domain + "," + access;
    }
}


