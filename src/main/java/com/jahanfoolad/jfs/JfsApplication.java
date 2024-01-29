package com.jahanfoolad.jfs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;

import java.util.Locale;

@SpringBootApplication
public class JfsApplication {

    public static void main(String[] args) {
        SpringApplication.run(JfsApplication.class, args);
    }

    @Configuration
    public static class AppConfig{
        @Bean
        public MessageSource faMessageSource() {
            ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
            messageSource.setBasename("message_fa");
            messageSource.setDefaultLocale(Locale.ENGLISH);
            messageSource.setDefaultEncoding("UTF-8");
            return messageSource;
        }

        @Bean
        public MessageSource enMessageSource() {
            ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
            messageSource.setBasename("message");
            messageSource.setDefaultLocale(Locale.ENGLISH);
            messageSource.setDefaultEncoding("UTF-8");
            return messageSource;
        }
    }

}
