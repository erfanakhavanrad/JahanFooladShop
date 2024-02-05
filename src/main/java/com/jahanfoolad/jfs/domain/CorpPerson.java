package com.jahanfoolad.jfs.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "CorpPerson")
@Getter
@Setter
public class CorpPerson extends AbstractEntity {

    //    private Role role;
    @Column
    private String name;
    @Column
    private String nationalId;
    @Column
    private String economicCode;
    @Column
    private String agentName;
    @Column
    private String cellPhone;
    @Column
    private String email;
    @Column
    private Boolean verified;
    @Column
    private Boolean activated;
    @Column
    private String password;
    @OneToMany(cascade = {CascadeType.ALL})
    private List<Contact> contactList;

}
