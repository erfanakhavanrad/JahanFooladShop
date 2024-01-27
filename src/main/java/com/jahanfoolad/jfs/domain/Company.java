package com.jahanfoolad.jfs.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "Company")
@Getter
@Setter
public class Company extends AbstractEntity{

    @Column
    private String name;
    @Column
    private String nationalId;
    @Column
    private String economicCode;
    @Column
    private String agentName;
    @Column
    private String cellphone;
    @Column
    private String email;
    @Column
    private Boolean verified;
    @Column
    private Boolean isActive;
    @OneToMany(cascade = {CascadeType.ALL} , fetch = FetchType.EAGER)
    private List<Contact> contactList;
//    @OneToMany(cascade = {CascadeType.ALL})
//    private List<Role> roleList;


}
