package com.jahanfoolad.jfs.service;

import com.jahanfoolad.jfs.domain.SmsRestResponse;

public interface SmsService {
    SmsRestResponse sendPasswordSms(String to ,String password);

}
