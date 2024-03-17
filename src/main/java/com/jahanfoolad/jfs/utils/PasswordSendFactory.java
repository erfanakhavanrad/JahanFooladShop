package com.jahanfoolad.jfs.utils;

import com.jahanfoolad.jfs.domain.CorpPerson;
import com.jahanfoolad.jfs.domain.Person;
import com.jahanfoolad.jfs.domain.RealPerson;
import com.jahanfoolad.jfs.domain.RegisterType;
import com.jahanfoolad.jfs.service.SmsService;
import com.jahanfoolad.jfs.service.impl.EmailServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.stream.Stream;

@Service
public class PasswordSendFactory {

    @Autowired
    EmailServices emailServices;

    @Autowired
    SmsService smsService;


    public void send(Person person, RegisterType registerType) {
        new Thread(() -> {

            switch (registerType) {
                case EMAIL:
                    emailServices.send(person.getEmail(), person.getPassword());
                    break;
                case PHONE:
                    smsService.sendPasswordSms(person.getCellPhone(), person.getPassword());
                    break;
                default:
                    return;
            }
        }).start();
    }

    public void dynamicSend(Person person, RegisterType registerType, String sendingFieldName) {
        // Method in work
        new Thread(() -> {
            String upperCaseField = sendingFieldName.substring(0, 1).toUpperCase() + sendingFieldName.substring(1);
            String modifiedFieldName = person + (".get" + sendingFieldName.substring(0, 1).toUpperCase() + sendingFieldName.substring(1) + "()");
//String
            try {
                Class<?> localClass = person.getClass();
                Field[] localClassFields = localClass.getDeclaredFields();
                Field[] abstractSuperClassFields =localClass.getSuperclass().getDeclaredFields();
                Field[] allFields = Stream.concat(Arrays.stream(localClassFields), Arrays.stream(abstractSuperClassFields)).toArray(Field[]::new);

                 Field field = allFields.getClass().getField(upperCaseField);
                field.setAccessible(true);

//                field.get();
            } catch (NoSuchFieldException e) {
                throw new RuntimeException(e);
            }
            switch (registerType) {
                case EMAIL:
                    emailServices.send(person.getEmail(), modifiedFieldName);
                    break;
                case PHONE:
                    smsService.sendPasswordSms(person.getCellPhone(), person + modifiedFieldName);
                    break;
                default:
                    return;
            }
        }).start();
    }

}