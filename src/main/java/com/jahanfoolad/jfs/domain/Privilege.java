package com.jahanfoolad.jfs.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "privilege")
@Getter
@Setter
public class Privilege extends AbstractEntity {
    @Column
    private String name;
    @Column
    private String domain;
    @Column
    private String access;
}
