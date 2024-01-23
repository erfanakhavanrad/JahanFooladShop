package com.jahanfoolad.jfs.service;

import com.jahanfoolad.jfs.domain.RealPerson;
import com.jahanfoolad.jfs.domain.dto.RealPersonDto;

import java.util.List;

public interface RealPersonService {
    RealPerson createRealPerson(RealPersonDto realPersonDto);
    RealPerson getRealPersonByUserId(Long id) throws Exception;
    List<RealPerson> getRealPersons();
    void deleteRealPerson(Long id);
}
