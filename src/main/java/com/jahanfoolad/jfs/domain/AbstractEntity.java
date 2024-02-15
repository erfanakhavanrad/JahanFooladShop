package com.jahanfoolad.jfs.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.util.Date;

@Getter
@Setter
@MappedSuperclass
abstract public class AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column
    @CreatedDate
    private Date createDate = new Date();

    @Column(name = "fk_user_modify")
    Long lastModifiedBy;

    @Column(name = "fk_user_created")
    Long createdBy;

    @Column(name = "description")
    private String description;

    @Column(name = "lastModifiedDate")
    private Date lastModifiedDate;

}
