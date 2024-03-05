package com.jahanfoolad.jfs;


import com.jahanfoolad.jfs.domain.RealPerson;
import com.jahanfoolad.jfs.service.impl.RealPersonServiceImpl;
import org.junit.Before;
import org.junit.Test;

public class PersonServiceImplTest {

    private RealPersonServiceImpl realPersonService;
    private RealPerson realPerson;

    @Before
    public void setup() {
        realPersonService = new RealPersonServiceImpl();
        realPerson = new RealPerson();
        realPerson.setCellPhone("09353368463");
    }

    @Test
    public void passwordGenerator() {
         realPersonService.generatePassword();
    }

}
