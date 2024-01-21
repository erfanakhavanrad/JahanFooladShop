package com.jahanfoolad.jfs.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Test extends AbstractEntity {
//    @Id
//    @Column
//    private Long id;

    @Column(name = "name")
    private String name;


//    @Override
//    public String toString() {
//        return "Test{" +
//                "id=" + id +
//                ", name='" + name + '\'' +
//                '}';
//    }
}
