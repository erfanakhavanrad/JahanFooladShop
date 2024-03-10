package com.jahanfoolad.jfs.service.impl;

import com.jahanfoolad.jfs.JfsApplication;
import com.jahanfoolad.jfs.domain.*;
import com.jahanfoolad.jfs.domain.dto.ContactDto;
import com.jahanfoolad.jfs.domain.dto.RealPersonDto;
import com.jahanfoolad.jfs.jpaRepository.ContactRepository;
import com.jahanfoolad.jfs.jpaRepository.RealPersonRepository;
import com.jahanfoolad.jfs.security.SecurityService;
import com.jahanfoolad.jfs.service.RealPersonService;
import com.jahanfoolad.jfs.service.SmsService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RealPersonServiceImpl implements RealPersonService {

    @Autowired
    RealPersonRepository realPersonRepository;

    @Autowired
    ContactRepository contactRepository;

    @Resource(name = "faMessageSource")
    private MessageSource faMessageSource;


    @Autowired
    ResponseModel responseModel;

    @Autowired
    PersonService<RealPerson> personService;

    @Autowired
    SecurityService securityService;

    @Override
    public Page<RealPerson> getRealPeople(Integer pageNo, Integer perPage) {
        return realPersonRepository.findAll(JfsApplication.createPagination(pageNo, perPage));
    }

//    private String newPassword;

    @Override
    public RealPerson getRealPersonById(Long id) throws Exception {
//        realUserRepository.findById(id).get();
        return realPersonRepository.findById(id).orElseThrow(() -> new Exception(faMessageSource.getMessage("REAL_PERSON_NOT_FOUND", null, Locale.ENGLISH)));
    }


    @Override
    public RealPerson getRealPersonByUsername(String userName) throws Exception {
        RealPerson byUserName = realPersonRepository.findByUserName(userName);
        if (byUserName != null) {
            return byUserName;
        } else throw new Exception(faMessageSource.getMessage("REAL_PERSON_NOT_FOUND", null, Locale.ENGLISH));
    }

    @Override
    public Page<RealPerson> findByProvince(ContactDto contactDto, Integer pageNo, Integer perPage) {
        List<Contact> contacts =  contactRepository.findAllByProvince(contactDto.getProvince());
        return realPersonRepository.findAllByContactListIn(contacts , JfsApplication.createPagination(pageNo , perPage));
    }

    @Override
    public Page<RealPerson> findByCity(ContactDto contactDto, Integer pageNo, Integer perPage) {
        List<Contact> contacts = contactRepository.findAllByCity(contactDto.getCity());
        return realPersonRepository.findAllByContactListIn(contacts ,JfsApplication.createPagination(pageNo , perPage));
    }


    // Can throw exception in Header
    @Override
    public RealPerson createRealPerson(RealPersonDto realPersonDto, HttpServletRequest request) throws Exception {
        ModelMapper modelMapper = new ModelMapper();
        RealPerson realPerson = modelMapper.map(realPersonDto, RealPerson.class);
        realPerson.setPassword(generatePassword());
        realPerson.setUserName(realPerson.getCellPhone());
//        realPerson.setCreatedBy((((Person) securityService.getUserByToken(request).getContent()).getId()));
        return personService.save(realPerson);
    }

    @Override
    public RealPerson updateRealPerson(RealPersonDto realPersonDto, HttpServletRequest httpServletRequest) throws Exception {
        responseModel.clear();
        ModelMapper modelMapper = new ModelMapper();

        RealPerson oldRealPerson = getRealPersonById(realPersonDto.getId());
        RealPerson newRealPerson = modelMapper.map(realPersonDto, RealPerson.class);
        RealPerson updated = (RealPerson) responseModel.merge(oldRealPerson, newRealPerson);

        if (newRealPerson.getContactList() != null && !newRealPerson.getContactList().isEmpty()) {
            updated.setContactList(newRealPerson.getContactList());
        }
        return realPersonRepository.save(updated);
    }

    @Override
    public void deleteRealPerson(Long id) {
        realPersonRepository.deleteById(id);
    }


    @Override
    public void resetPass(RealPerson byMobile, String newPassword) throws Exception {
        RealPerson realPerson = realPersonRepository.findById(byMobile.getId()).orElseThrow(() -> new Exception(faMessageSource.getMessage("NOT_FOUND", null, Locale.ENGLISH)));
        realPerson.setPassword(newPassword);
//        realPersonRepository.
    }

    @Override
    public ResponseModel login(RealPerson realPerson, HttpServletRequest request) {
        try {
            return personService.login(realPerson, request);
        } catch (Exception e) {
            responseModel.setResult(personService.fail);
            responseModel.setError(e.getMessage());
            responseModel.setError(e.toString());
            return responseModel;
        }
    }

    @Override
    public RealPerson findByMobile(RealPerson realPerson) throws Exception {
        RealPerson userByPhoneNumber = realPersonRepository.findByCellPhone(realPerson.getCellPhone());
        if (userByPhoneNumber == null)
            throw new Exception(faMessageSource.getMessage("NOT_FOUND", null, Locale.ENGLISH));
        return userByPhoneNumber;
    }

    @Autowired
    SmsService smsService;

    @Override
    public void resetPass(String userName) throws Exception {
        RealPerson byUserName = realPersonRepository.findByUserName(userName);
        byUserName.setConfirmationCode(generatePassword());
        smsService.sendPasswordSms(byUserName.getCellPhone(),  byUserName.getConfirmationCode());
        realPersonRepository.save(byUserName);
    }

    @Override
    public void resetPassConfirm(String userName, String password) throws Exception {
        RealPerson byUserName = realPersonRepository.findByUserName(userName);
        if (Objects.equals(byUserName.getConfirmationCode(), password)) {
            byUserName.setPassword(byUserName.getConfirmationCode());
            realPersonRepository.save(byUserName);
        } else throw new Exception(faMessageSource.getMessage("UNKNOWN_TRANSACTION_ERROR", null, Locale.ENGLISH));
    }


     public String generatePassword() {
        return String.format("%06d", new Random().nextInt(1000000));
    }
//        public String generatePassword(RealPerson realPerson) {
//        List<Character> characters = new ArrayList<>();
//        String lastFourCharacters = realPerson.getCellPhone().substring(realPerson.getCellPhone().length() - 4);
//        for (char c : lastFourCharacters.toCharArray()) {
//            characters.add(c);
//        }
//        StringBuilder output = new StringBuilder(lastFourCharacters.length());
//        while (characters.size() != 0) {
//            int randPicker = (int) (Math.random() * characters.size());
//            output.append(characters.remove(randPicker));
//        }
//        return output.toString();
//    }


}
