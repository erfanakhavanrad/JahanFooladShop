package com.jahanfoolad.jfs.service.impl;

import com.google.gson.Gson;
import com.jahanfoolad.jfs.domain.SmsRestResponse;
import com.jahanfoolad.jfs.service.SmsService;
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
    @Resource(name = "enMessageSource")
    private MessageSource enMessageSource;

    private final String endPoint = "https://rest.payamak-panel.com/api/%s";

    //    private final String SMS_TEXT;
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


    public SmsRestResponse SendSMS(String to, String text, Boolean isFlash) {
        String operation = new Object() {
        }.getClass().getEnclosingMethod().getName();
        String url = String.format(endPoint, "SendSMS/" + operation);
        Map<String, Object> map = new HashMap<>();
        map.put("username", this.userName);
        map.put("password", this.password);
        map.put("to", to);
        map.put("from", from);
        map.put("text", faMessageSource.getMessage("sms_welcome_message", null, Locale.ENGLISH) + "\n "+text);//#CODE: // لغو 11
        map.put("isFlash", isFlash);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(map, getHttpHeaders());

        ResponseEntity<SmsRestResponse> response = this.restTemplate.postForEntity(url, entity, SmsRestResponse.class);
        return response.getBody();
    }
}
