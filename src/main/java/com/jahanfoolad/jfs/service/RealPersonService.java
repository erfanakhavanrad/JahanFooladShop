package com.jahanfoolad.jfs.service;

import com.jahanfoolad.jfs.domain.RealPerson;
import com.jahanfoolad.jfs.domain.dto.RealPersonDto;

import java.util.List;

public interface RealPersonService {
    List<RealPerson> getRealPersons();
    RealPerson getRealPersonByUserId(Long id) throws Exception;
    RealPerson createRealPerson(RealPersonDto realPersonDto);
    void deleteRealPerson(Long id);
}
