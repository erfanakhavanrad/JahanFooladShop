package com.jahanfoolad.jfs.service;

import com.jahanfoolad.jfs.domain.Contact;
import com.jahanfoolad.jfs.domain.RealPerson;
import com.jahanfoolad.jfs.domain.dto.ContactDto;
import com.jahanfoolad.jfs.domain.dto.RealPersonDto;

import java.util.List;

public interface UserService {
    RealPerson createUser(RealPersonDto realPersonDto);
    RealPerson getUserByUserId(Long id) throws Exception;

    List<RealPerson> getUsers();

    void deleteUser(Long id);

    Contact addContact(ContactDto contactDto);

}
