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


//    @Column(name = "fk_person")
//    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
//    private Users lastModifiedBy;

//    private Integer lastModifiedBy;

//    @Column(name = "fk_person")
//    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
//    private Users createdBy;
//    private Integer lastModifiedBy;

    @Column(name = "description")
    private String description;

    @Column(name = "lastModifiedDate")
    private Date lastModifiedDate;

}
