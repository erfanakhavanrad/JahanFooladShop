package com.jahanfoolad.jfs.service;

import com.jahanfoolad.jfs.domain.Test;
import com.jahanfoolad.jfs.jpaRepository.TstInter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestService {

    @Autowired
    TstInter tstInter;

    public String getAll() {
        return tstInter.findAll().toString();
    }

    public void addAll() {
        Test test = new Test();
        test.setId(12L);
        test.setName("Akhavan");
        tstInter.save(test);
    }
}
