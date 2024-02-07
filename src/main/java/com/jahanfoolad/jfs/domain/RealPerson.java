package com.jahanfoolad.jfs.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Entity
@Table(name = "RealPerson")
@Getter
@Setter
public class RealPerson extends AbstractEntity {

    //    private Role role;
    @Column
    private String firstName;
    @Column
    private String userName;
    @Column
    private String lastName;
    @Column
    private String nationalNumber;
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
