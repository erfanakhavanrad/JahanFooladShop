package com.jahanfoolad.jfs.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "File")
@Getter
@Setter
public class File extends AbstractEntity {

    @Column
    private String title;
    @Column
    private Long name;
    @Column
    private String extension;
    @Column
    private String url;
    @Column
    private Boolean isMain = false;
    @Column
    @NotNull
    private Long companyId;

}
