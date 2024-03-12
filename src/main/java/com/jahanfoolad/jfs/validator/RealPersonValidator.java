package com.jahanfoolad.jfs.validator;

import com.jahanfoolad.jfs.domain.dto.RealPersonDto;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.Locale;

@Component
@Slf4j
public class RealPersonValidator {

    @Resource(name = "faMessageSource")
    private MessageSource faMessageSource;

    public void addValidator(RealPersonDto realPersonDto) throws Exception {
        checkPhone(realPersonDto.getCellPhone());
        checkNationalNumber(realPersonDto.getNationalNumber());
//        nullChecker(realPersonDto);
    }

    private void checkPhone(String cellPhone) throws Exception {
        if (!(cellPhone.matches("[0-9]+") && cellPhone.length() > 2)) {
            throw new Exception(faMessageSource.getMessage("PHONE_NUMBER_NOT_VALID", null, Locale.ENGLISH));
        }
    }

    private void checkNationalNumber(String nationalNumber) throws Exception {
        if (nationalNumber.length() == 10) {

            if (nationalNumber.length() < 8 || Integer.parseInt(nationalNumber, 10) == 0)
                throw new Exception(faMessageSource.getMessage("NATIONAL_NUMBER_NOT_VALID", null, Locale.ENGLISH));
            nationalNumber = ("0000" + nationalNumber).substring(nationalNumber.length() + 4 - 10);
            if (Integer.parseInt(nationalNumber.substring(3, 6), 10) == 0)
                throw new Exception(faMessageSource.getMessage("NATIONAL_NUMBER_NOT_VALID", null, Locale.ENGLISH));
            int c = Integer.parseInt(nationalNumber.substring(9, 10), 10);
            int s = 0;
            for (int i = 0; i < 9; i++)
                s += Integer.parseInt(nationalNumber.substring(i, i + 1), 10) * (10 - i);
            s = s % 11;
            if (!((s < 2 && c == s) || (s >= 2 && c == (11 - s)))) {
                throw new Exception(faMessageSource.getMessage("NATIONAL_NUMBER_NOT_VALID", null, Locale.ENGLISH));
            }

        } else {
            throw new Exception(faMessageSource.getMessage("NATIONAL_NUMBER_NOT_VALID", null, Locale.ENGLISH));
        }
    }

    private void nullChecker(RealPersonDto realPersonDto) throws IllegalAccessException {
        for (Field field : realPersonDto.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            log.info("LOGGING FIELDSSSSSSSSSSSSSSSSSSSS");
            Object values = field.get(realPersonDto);
            System.out.println(values.toString());
        }
    }


}
