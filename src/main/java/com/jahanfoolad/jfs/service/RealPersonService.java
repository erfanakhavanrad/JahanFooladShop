package com.jahanfoolad.jfs.service;

import com.jahanfoolad.jfs.domain.RealPerson;
import com.jahanfoolad.jfs.domain.dto.RealPersonDto;

import java.util.List;

public interface RealPersonService {
    List<RealPerson> getRealPersons();

    RealPerson getRealPersonById(Long id) throws Exception;

    RealPerson getRealPersonByUsername(String userName) throws Exception;

    RealPerson createRealPerson(RealPersonDto realPersonDto);

    RealPerson updateRealPerson(RealPersonDto realPersonDto) throws Exception;

    void deleteRealPerson(Long id);

    RealPerson findByMobile(RealPerson realPerson) throws Exception;

    void resetPass(String userName) throws Exception;

    void resetPassConfirm(String userName, String newPassword) throws Exception;

    RealPerson login(RealPerson realPerson) throws Exception;

    void resetPass(RealPerson byMobile, String newPassword) throws Exception;

    String generatePassword(RealPerson realPerson);

}
