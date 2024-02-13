package com.jahanfoolad.jfs.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "Category")
@Getter
@Setter
public class Category extends AbstractEntity {
    @Column
    private Long parentId;
    @Column
    private String name;
    @OneToOne(cascade = CascadeType.ALL)
    private File thumbnail;
}
