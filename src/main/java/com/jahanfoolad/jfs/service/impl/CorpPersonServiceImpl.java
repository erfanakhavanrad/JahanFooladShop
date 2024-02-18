package com.jahanfoolad.jfs.service.impl;

import com.jahanfoolad.jfs.JfsApplication;
import com.jahanfoolad.jfs.domain.Contact;
import com.jahanfoolad.jfs.domain.CorpPerson;
import com.jahanfoolad.jfs.domain.ResponseModel;
import com.jahanfoolad.jfs.domain.dto.ContactDto;
import com.jahanfoolad.jfs.domain.dto.CorpPersonDto;
import com.jahanfoolad.jfs.jpaRepository.CorpPersonRepository;
import com.jahanfoolad.jfs.service.CorpPersonService;
import jakarta.annotation.Resource;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
public class CorpPersonServiceImpl implements CorpPersonService {

    @Autowired
    CorpPersonRepository corpPersonRepository;

    @Resource(name = "faMessageSource")
    private MessageSource faMessageSource;
    @Resource(name = "enMessageSource")
    private MessageSource enMessageSource;

    @Autowired
    ResponseModel responseModel;

    @Override
    public List<CorpPerson> getCorpPeople() {
        return corpPersonRepository.findAll();
    }

    @Override
    public CorpPerson getCorpPersonById(Long id) throws Exception {
        return corpPersonRepository.findById(id).orElseThrow(() -> new Exception(faMessageSource.getMessage("NOT_FOUND", null, Locale.ENGLISH)));
    }

    @Override
    public CorpPerson createCorpPerson(CorpPersonDto corpPersonDto) {
        ModelMapper modelMapper = new ModelMapper();
        CorpPerson corpPerson = modelMapper.map(corpPersonDto, CorpPerson.class);
        return modelMapper.map(corpPersonRepository.save(corpPerson), CorpPerson.class);
    }

    @Override
    public CorpPerson updateCorpPerson(CorpPersonDto corpPersonDto) throws Exception {
        ModelMapper modelMapper = new ModelMapper();

        CorpPerson oldCorpPerson = getCorpPersonById(corpPersonDto.getId());
        CorpPerson newCorpPerson = modelMapper.map(corpPersonDto, CorpPerson.class);

        responseModel.clear();
        CorpPerson updated = (CorpPerson) responseModel.merge(oldCorpPerson, newCorpPerson);

        if (newCorpPerson.getContactList() != null && !newCorpPerson.getContactList().isEmpty()) {
            updated.setContactList(newCorpPerson.getContactList());
        }

        return corpPersonRepository.save(updated);
    }

    @Override
    public Page<CorpPerson> findByContact(ContactDto contactDto, Integer pageNo, Integer perPage) {
        ModelMapper modelMapper = new ModelMapper();
        Contact contact = modelMapper.map(contactDto, Contact.class);
        List<Contact> contactList = new ArrayList<>();
        contact.setId(452L);
        contactList.add(contact);
        return corpPersonRepository.findAllByContactListIn(contactList,JfsApplication.createPagination(pageNo, perPage));
    }

    @Override
    public void deleteCorpPerson(Long id) {
        corpPersonRepository.deleteById(id);
    }

    @Override
    public CorpPerson login(CorpPerson corpPerson) throws Exception {
        CorpPerson userByPhoneNumber = corpPersonRepository.findByCellPhone(corpPerson.getCellPhone());
        if (userByPhoneNumber == null)
            throw new Exception(faMessageSource.getMessage("NOT_FOUND", null, Locale.ENGLISH));
        if (!userByPhoneNumber.getPassword().equals(corpPerson.getPassword())) {
            throw new Exception(faMessageSource.getMessage("INCORRECT_PASSWORD", null, Locale.ENGLISH));
        }
        return userByPhoneNumber;
    }


}
