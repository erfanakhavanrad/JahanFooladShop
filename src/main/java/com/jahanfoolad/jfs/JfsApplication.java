package com.jahanfoolad.jfs;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.BeansException;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Locale;

@Configuration
//@SpringBootApplication
@SpringBootApplication(exclude = org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class)
@EnableScheduling
@EnableAsync
public class JfsApplication implements ApplicationContextAware, CommandLineRunner {

    public static BCryptPasswordEncoder bCryptPasswordEncoder;
    public ApplicationContext context;
    final static int PER_PAGE = 20;
    final static int MAX_PAGE_SIZE = 10000;

    public static void main(String[] args) {
        SpringApplication.run(JfsApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        bCryptPasswordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }

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


    public static Pageable createPagination(Integer pageNumber, Integer perPage) {
        if (pageNumber == null)
            return PageRequest.of(0, MAX_PAGE_SIZE, Sort.by(Sort.Direction.DESC, "createDate"));
        else if (perPage == null)
            return PageRequest.of(--pageNumber, PER_PAGE, Sort.by(Sort.Direction.DESC, "createDate"));
        else
            return PageRequest.of(--pageNumber, perPage, Sort.by(Sort.Direction.DESC, "createDate"));
    }

    public static String getClientIP(HttpServletRequest request) {
        String xfHeader = request.getHeader("X-Forwarded-For");
        if (xfHeader == null) {
            return request.getRemoteAddr();
        }
        return xfHeader.split(",")[0];
    }

}
