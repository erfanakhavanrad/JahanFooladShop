package com.jahanfoolad.jfs.service;

import com.jahanfoolad.jfs.domain.SmsRestResponse;

public interface SmsService {
    SmsRestResponse SendSMS(String to, String text, Boolean isFlash);

}
