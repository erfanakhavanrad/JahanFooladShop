package com.jahanfoolad.jfs.service;

import com.jahanfoolad.jfs.domain.SmsRestResponse;

public interface SmsService {
    public SmsRestResponse SendSMS(String to, String text, Boolean isFlash);
}
