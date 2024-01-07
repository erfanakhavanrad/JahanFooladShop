package com.jahanfoolad.jfs;

import com.jahanfoolad.jfs.domain.Test;
import com.jahanfoolad.jfs.jpaRepository.TstInter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JfsApplication {

    public static void main(String[] args) {
        SpringApplication.run(JfsApplication.class, args);
    }

}
