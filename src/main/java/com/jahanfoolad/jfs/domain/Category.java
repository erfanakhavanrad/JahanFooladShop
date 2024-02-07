package com.jahanfoolad.jfs.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "Category")
@Getter
@Setter
public class Category extends AbstractEntity {
   @Column
    private String name;
   @Column
   private String fileName;
}
