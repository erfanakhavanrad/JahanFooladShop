package com.jahanfoolad.jfs.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="File")
@Getter
@Setter
public class File extends AbstractEntity{

    @Column
    private String name;
    @Column
    private String extension;
    @Column
    private String url;

}
