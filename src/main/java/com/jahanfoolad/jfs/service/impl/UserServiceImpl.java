package com.jahanfoolad.jfs.service.impl;

import com.jahanfoolad.jfs.domain.Contact;
import com.jahanfoolad.jfs.domain.RealPerson;
import com.jahanfoolad.jfs.domain.dto.ContactDto;
import com.jahanfoolad.jfs.domain.dto.RealPersonDto;
import com.jahanfoolad.jfs.jpaRepository.ContactRepository;
import com.jahanfoolad.jfs.jpaRepository.RealUserRepository;
import com.jahanfoolad.jfs.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    RealUserRepository realUserRepository;

    @Autowired
    ContactRepository contactRepository;

    // Can throw exception in Header
    @Override
    public RealPerson createUser(RealPersonDto realPersonDto) {
        ModelMapper modelMapper = new ModelMapper();
        RealPerson realPerson = modelMapper.map(realPersonDto, RealPerson.class);
//        List<Contact> contactList = new ArrayList<>();
//        for (int i = 0; i < realPersonDto.getContactDtoList().size(); i++) {
//            Contact contact = modelMapper.map(realPersonDto.getContactDtoList().get(i), Contact.class);
//            contactList.add(contact);
//        }
//        realPerson.setContactList(contactList);

//        RealPerson newUser = realUserRepository.save(realPerson);
        return modelMapper.map(realUserRepository.save(realPerson), RealPerson.class);
    }

    @Override
    public RealPerson getUserByUserId(Long id) throws Exception {
//        realUserRepository.findById(id).get();
        return realUserRepository.findById(id).orElseThrow(() -> new Exception("User not found."));
    }


    @Override
    public List<RealPerson> getUsers() {
        return realUserRepository.findAll();
    }

    @Override
    public void deleteUser(Long id) {
        realUserRepository.deleteById(id);
    }

    @Override
    public Contact addContact(ContactDto contactDto) {
        ModelMapper modelMapper = new ModelMapper();
        Contact contact = modelMapper.map(contactDto, Contact.class);
//        return modelMapper.map(contactRepository.save(contactDto));
//        return modelMapper.map(contactRepository.save(contactDto));
        return null;
    }


}
