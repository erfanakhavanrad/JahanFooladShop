package com.jahanfoolad.jfs.service;

import com.jahanfoolad.jfs.domain.RealPerson;
import com.jahanfoolad.jfs.domain.ResponseModel;
import com.jahanfoolad.jfs.domain.dto.RealPersonDto;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface RealPersonService {
    List<RealPerson> getRealPersons() throws Exception;

    RealPerson getRealPersonById(Long id) throws Exception;

    RealPerson getRealPersonByUsername(String userName) throws Exception;

    RealPerson createRealPerson(RealPersonDto realPersonDto, HttpServletRequest request) throws Exception;

    RealPerson updateRealPerson(RealPersonDto realPersonDto, HttpServletRequest httpServletRequest) throws Exception;

    void deleteRealPerson(Long id) throws Exception;

    RealPerson findByMobile(RealPerson realPerson) throws Exception;

    void resetPass(String userName) throws Exception;

    void resetPassConfirm(String userName, String newPassword) throws Exception;

    ResponseModel login(RealPerson realPerson, HttpServletRequest request) throws Exception;

    void resetPass(RealPerson byMobile, String newPassword) throws Exception;

    String generatePassword(RealPerson realPerson);

}
