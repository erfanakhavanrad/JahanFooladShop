package com.jahanfoolad.jfs.service.impl;

import com.jahanfoolad.jfs.domain.RealPerson;
import com.jahanfoolad.jfs.domain.dto.RealPersonDto;
import com.jahanfoolad.jfs.jpaRepository.RealPersonRepository;
import com.jahanfoolad.jfs.service.RealPersonService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RealPersonServiceImpl implements RealPersonService {

    @Autowired
    RealPersonRepository realPersonRepository;

    @Override
    public List<RealPerson> getRealPersons() {
        return realPersonRepository.findAll();
    }

    @Override
    public RealPerson getRealPersonByUserId(Long id) throws Exception {
//        realUserRepository.findById(id).get();
        return realPersonRepository.findById(id).orElseThrow(() -> new Exception("User not found."));
    }

    // Can throw exception in Header
    @Override
    public RealPerson createRealPerson(RealPersonDto realPersonDto) {
        ModelMapper modelMapper = new ModelMapper();
        RealPerson realPerson = modelMapper.map(realPersonDto, RealPerson.class);
//        List<Contact> contactList = new ArrayList<>();
//        for (int i = 0; i < realPersonDto.getContactDtoList().size(); i++) {
//            Contact contact = modelMapper.map(realPersonDto.getContactDtoList().get(i), Contact.class);
//            contactList.add(contact);
//        }
//        realPerson.setContactList(contactList);

//        RealPerson newUser = realUserRepository.save(realPerson);
        return modelMapper.map(realPersonRepository.save(realPerson), RealPerson.class);
    }


    @Override
    public void deleteRealPerson(Long id) {
        realPersonRepository.deleteById(id);
    }

}
