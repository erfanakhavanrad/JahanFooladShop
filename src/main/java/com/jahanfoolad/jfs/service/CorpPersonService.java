package com.jahanfoolad.jfs.service;

import com.jahanfoolad.jfs.domain.Contact;
import com.jahanfoolad.jfs.domain.CorpPerson;
import com.jahanfoolad.jfs.domain.dto.ContactDto;
import com.jahanfoolad.jfs.domain.dto.CorpPersonDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CorpPersonService {
    List<CorpPerson> getCorpPeople();

    CorpPerson getCorpPersonById(Long id) throws Exception;

    CorpPerson createCorpPerson(CorpPersonDto corpPersonDto);

    CorpPerson updateCorpPerson(CorpPersonDto corpPersonDto) throws Exception;

    Page<CorpPerson> findByContact(ContactDto contactDto, Integer pageNo, Integer perPage);

    void deleteCorpPerson(Long id);

    CorpPerson login(CorpPerson corpPerson) throws Exception;

}
