package com.jahanfoolad.jfs.service.impl;

import com.google.gson.Gson;
import com.jahanfoolad.jfs.domain.SmsRestResponse;
import com.jahanfoolad.jfs.service.SmsService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@Service
public class SmsServiceImpl implements SmsService {

    private final RestTemplate restTemplate;
    private final Gson gson;

    @Value("${sms.username}")
    private String userName;

    @Value("${sms.password}")
    private String password;

    @Value("${sms.privateLine}")
    private String from;

    @Resource(name = "faMessageSource")
    private MessageSource faMessageSource;

    private static final String SMS_WELCOME_MESSAGE_NEW_PASSWORD = "به آهن\u200Cسی خوش آمدید. رمز عبور شما ";
    private static final String SEND_SMS = "SendSMS/SendSMS";
    private final String baseUrl = "https://rest.payamak-panel.com/api/%s";

    public SmsServiceImpl(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
        gson = new Gson();
    }

    private HttpHeaders getHttpHeaders() {
        // create headers
        HttpHeaders headers = new HttpHeaders();
        // set 'content-type' header
        headers.setContentType(MediaType.APPLICATION_JSON);
        // set 'accept' header
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        return headers;
    }

    @Override
    public SmsRestResponse sendPasswordSms(String to, String password) {

        String url = String.format(baseUrl, SEND_SMS);
        Map<String, Object> map = new HashMap<>();
        map.put("username", this.userName);
        map.put("password", this.password);
        map.put("to", to);
        map.put("isFlash", false);
        map.put("from", from);
        map.put("text", SMS_WELCOME_MESSAGE_NEW_PASSWORD + "\n\n " + password);//#CODE: // لغو 11

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(map, getHttpHeaders());

        ResponseEntity<SmsRestResponse> response = this.restTemplate.postForEntity(url, entity, SmsRestResponse.class);
        return response.getBody();
    }
}
