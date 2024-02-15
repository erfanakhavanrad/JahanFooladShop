package com.jahanfoolad.jfs.domain;

import com.jahanfoolad.jfs.utils.CONSTANTS;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@DiscriminatorValue(CONSTANTS.CORP_PERSON_DISC)
public class CorpPerson extends Person {

    @Column
    private String nationalId;
    @Column
    private String economicCode;
    @Column
    private String agentName;

}
