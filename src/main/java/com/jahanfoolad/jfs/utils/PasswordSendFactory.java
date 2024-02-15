package com.jahanfoolad.jfs.utils;

import com.jahanfoolad.jfs.domain.CorpPerson;
import com.jahanfoolad.jfs.domain.Person;
import com.jahanfoolad.jfs.domain.RealPerson;
import com.jahanfoolad.jfs.domain.RegisterType;
import com.jahanfoolad.jfs.service.SmsService;
import com.jahanfoolad.jfs.service.impl.EmailServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PasswordSendFactory {

    @Autowired
    EmailServices emailServices;

    @Autowired
    SmsService smsService;


    public void send(Person person , RegisterType registerType) {
        new Thread(() -> {

            switch (registerType) {
                case EMAIL:
                    emailServices.send(person.getEmail(), person.getPassword());
                    break;
                case PHONE:
                    smsService.sendPasswordSms(person.getCellPhone(), person.getPassword());
                    break;
                default: return;
            }
        }).start();
    }
}