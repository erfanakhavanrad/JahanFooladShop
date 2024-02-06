package com.jahanfoolad.jfs.service.impl;

import com.jahanfoolad.jfs.domain.RealPerson;
import com.jahanfoolad.jfs.domain.dto.RealPersonDto;
import com.jahanfoolad.jfs.jpaRepository.RealPersonRepository;
import com.jahanfoolad.jfs.service.RealPersonService;
import jakarta.annotation.Resource;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
public class RealPersonServiceImpl implements RealPersonService {

    @Autowired
    RealPersonRepository realPersonRepository;

    @Resource(name = "faMessageSource")
    private MessageSource faMessageSource;
    @Resource(name = "enMessageSource")
    private MessageSource enMessageSource;

    @Override
    public List<RealPerson> getRealPersons() {
        return realPersonRepository.findAll();
    }

    @Override
    public RealPerson getRealPersonByUserId(Long id) throws Exception {
//        realUserRepository.findById(id).get();
        return realPersonRepository.findById(id).orElseThrow(() -> new Exception(enMessageSource.getMessage("item_not_found_message", null, Locale.ENGLISH)));
    }

    // Can throw exception in Header
    @Override
    public RealPerson createRealPerson(RealPersonDto realPersonDto) {
        ModelMapper modelMapper = new ModelMapper();
        RealPerson realPerson = modelMapper.map(realPersonDto, RealPerson.class);
        realPerson.setPassword(generatePassword(realPerson));
        return modelMapper.map(realPersonRepository.save(realPerson), RealPerson.class);
    }

    public String generatePassword(RealPerson realPerson) {
        List<Character> characters = new ArrayList<>();
        String lastFourCharacters = realPerson.getCellPhone().substring(realPerson.getCellPhone().length() - 4);
        for (char c : lastFourCharacters.toCharArray()) {
            characters.add(c);
        }
        StringBuilder output = new StringBuilder(lastFourCharacters.length());
        while (characters.size() != 0) {
            int randPicker = (int) (Math.random() * characters.size());
            output.append(characters.remove(randPicker));
        }
        return output.toString();
    }

    @Override
    public void deleteRealPerson(Long id) {
        realPersonRepository.deleteById(id);
    }

    @Override
    public RealPerson login(RealPerson realPerson) throws Exception {
//        RealPerson userByPhoneNumber = realPersonRepository.findByCellPhone(realPerson.getCellPhone());
//        if (userByPhoneNumber == null)
//            throw new Exception(enMessageSource.getMessage("item_not_found_message", null, Locale.ENGLISH));
        RealPerson userByPhoneNumber = findByMobile(realPerson);
        if (!userByPhoneNumber.getPassword().equals(realPerson.getPassword())) {
            throw new Exception(enMessageSource.getMessage("incorrect_password", null, Locale.ENGLISH));
        }
        return userByPhoneNumber;
    }

    @Override
    public RealPerson findByMobile(RealPerson realPerson) throws Exception {
        RealPerson userByPhoneNumber = realPersonRepository.findByCellPhone(realPerson.getCellPhone());
        if (userByPhoneNumber == null)
            throw new Exception(enMessageSource.getMessage("item_not_found_message", null, Locale.ENGLISH));
        return userByPhoneNumber;
    }

    @Override
    public void resetPass(RealPerson byMobile, String newPassword) throws Exception {
        RealPerson realPerson = realPersonRepository.findById(byMobile.getId()).orElseThrow(() -> new Exception(enMessageSource.getMessage("item_not_found_message", null, Locale.ENGLISH)));
        realPerson.setPassword(newPassword);
//        realPersonRepository.
    }
}
