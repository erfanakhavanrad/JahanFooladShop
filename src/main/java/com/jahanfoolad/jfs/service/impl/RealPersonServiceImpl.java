package com.jahanfoolad.jfs.service.impl;

import com.jahanfoolad.jfs.domain.RealPerson;
import com.jahanfoolad.jfs.domain.ResponseModel;
import com.jahanfoolad.jfs.domain.dto.RealPersonDto;
import com.jahanfoolad.jfs.jpaRepository.RealPersonRepository;
import com.jahanfoolad.jfs.service.RealPersonService;
import com.jahanfoolad.jfs.service.SmsService;
import jakarta.annotation.Resource;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

@Service
public class RealPersonServiceImpl implements RealPersonService {

    @Autowired
    RealPersonRepository realPersonRepository;

    @Resource(name = "faMessageSource")
    private MessageSource faMessageSource;
    @Resource(name = "enMessageSource")
    private MessageSource enMessageSource;

    @Autowired
    ResponseModel responseModel;

    @Override
    public List<RealPerson> getRealPersons() {
        return realPersonRepository.findAll();
    }

//    private String newPassword;

    @Override
    public RealPerson getRealPersonById(Long id) throws Exception {
//        realUserRepository.findById(id).get();
        return realPersonRepository.findById(id).orElseThrow(() -> new Exception("User not found."));
    }


    @Override
    public RealPerson getRealPersonByUsername(String userName) throws Exception {
        RealPerson byUserName = realPersonRepository.findByUserName(userName);
        if (byUserName != null) {
            return byUserName;
        } else throw new Exception(enMessageSource.getMessage("item_not_found_message", null, Locale.ENGLISH));
    }


    // Can throw exception in Header
    @Override
    public RealPerson createRealPerson(RealPersonDto realPersonDto) {
        ModelMapper modelMapper = new ModelMapper();
        RealPerson realPerson = modelMapper.map(realPersonDto, RealPerson.class);
        realPerson.setPassword(generatePassword(realPerson));
        realPerson.setUserName(realPerson.getCellPhone());
        RealPerson save = realPersonRepository.save(realPerson);
        smsService.SendSMS(realPersonDto.getCellPhone(), realPerson.getPassword(), false);
        return save;
    }

    @Override
    public RealPerson updateRealPerson(RealPersonDto realPersonDto) throws Exception {
        ModelMapper modelMapper = new ModelMapper();

        RealPerson oldRealPerson = getRealPersonById(realPersonDto.getId());
        RealPerson newRealPerson = modelMapper.map(realPersonDto, RealPerson.class);

        responseModel.clear();
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
        RealPerson realPerson = realPersonRepository.findById(byMobile.getId()).orElseThrow(() -> new Exception(enMessageSource.getMessage("item_not_found_message", null, Locale.ENGLISH)));
        realPerson.setPassword(newPassword);
//        realPersonRepository.
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

    @Autowired
    SmsService smsService;

    @Override
    public void resetPass(String userName) throws Exception {
        RealPerson byUserName = realPersonRepository.findByUserName(userName);
        byUserName.setConfirmationCode(generatePassword(byUserName));
        smsService.SendSMS(byUserName.getCellPhone(), "Code: " + byUserName.getConfirmationCode(), false);
        realPersonRepository.save(byUserName);
    }

    @Override
    public void resetPassConfirm(String userName, String password) throws Exception {
        RealPerson byUserName = realPersonRepository.findByUserName(userName);
        if (Objects.equals(byUserName.getConfirmationCode(), password)) {
            byUserName.setPassword(byUserName.getConfirmationCode());
            realPersonRepository.save(byUserName);
        } else throw new Exception(enMessageSource.getMessage("failed_message", null, Locale.ENGLISH));
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


}
