package com.jahanfoolad.jfs.service;

import com.jahanfoolad.jfs.domain.CorpPerson;
import com.jahanfoolad.jfs.domain.dto.CorpPersonDto;

import java.util.List;

public interface CorpPersonService {
    List<CorpPerson> getCorpPeople();

    CorpPerson getCorpPersonByUserId(Long id) throws Exception;

    CorpPerson createCorpPerson(CorpPersonDto corpPersonDto);

    void deleteCorpPerson(Long id);

    CorpPerson login(CorpPerson corpPerson) throws Exception;

}
