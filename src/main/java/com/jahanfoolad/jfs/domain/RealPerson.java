package com.jahanfoolad.jfs.domain;

import com.jahanfoolad.jfs.utils.CONSTANTS;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Entity
@Getter
@Setter
@DiscriminatorValue(CONSTANTS.REAL_PERSON_DISC)
public class RealPerson extends Person {

    @Column
    private String firstName;
    @Column
    private String lastName;
    @Column
    private String nationalNumber;
}


