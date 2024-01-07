package com.jahanfoolad.jfs.controller;

import com.jahanfoolad.jfs.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    TestService testService;

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public String test() {
        testService.addAll();
        return testService.getAll();
    }

}
