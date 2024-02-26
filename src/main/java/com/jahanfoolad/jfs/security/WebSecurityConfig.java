package com.jahanfoolad.jfs.security;

import com.jahanfoolad.jfs.domain.Person;
import com.jahanfoolad.jfs.service.impl.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
//@EnableWebSecurity
public class WebSecurityConfig implements WebMvcConfigurer {

    @Autowired
    PersonService userDetailsService;

    @Autowired
    CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    @Bean
    public AuthenticationTokenFilter authenticationTokenFilterBean() throws Exception {
        return new AuthenticationTokenFilter();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder()).and().build();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(httpSecurityCsrfConfigurer -> {
            httpSecurityCsrfConfigurer.disable();
        });
        http.sessionManagement(httpSecuritySessionManagementConfigurer -> {
            httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        });
        http.authorizeHttpRequests(authorizeReq -> authorizeReq
                .requestMatchers(HttpMethod.GET, "/privilege/getAll").permitAll()
                .requestMatchers(HttpMethod.POST, "/privilege/add").permitAll()
                .requestMatchers(HttpMethod.GET, "/product/*").permitAll()
                .requestMatchers(HttpMethod.POST, "/product/*").permitAll()
                .requestMatchers(HttpMethod.POST, "/file/*").permitAll()
                .requestMatchers(HttpMethod.POST, "/company/*").permitAll()
                .requestMatchers(HttpMethod.PATCH, "/product/update").permitAll()
                .requestMatchers(HttpMethod.GET, "/category/*").permitAll()

                .requestMatchers(HttpMethod.POST, "/realperson/save").permitAll()
                .requestMatchers(HttpMethod.POST, "/realperson/login").permitAll()
                .requestMatchers(HttpMethod.GET, "/user/forgetPass").permitAll()
                .requestMatchers(HttpMethod.POST, "/user/support").permitAll()
                .requestMatchers(HttpMethod.POST, "/user/login").permitAll()
                .requestMatchers(HttpMethod.GET, "/user/getToken").permitAll()
                .requestMatchers(HttpMethod.GET, "/user/getAll").authenticated()
                .requestMatchers(HttpMethod.GET, "/user/getById").authenticated()
                .requestMatchers(HttpMethod.GET, "/user/activation").authenticated()

                .requestMatchers(HttpMethod.GET, "/basket/*").permitAll()
                .requestMatchers(HttpMethod.DELETE, "/basket/*").permitAll()
                .requestMatchers(HttpMethod.POST, "/basket/add").permitAll()

                .requestMatchers(HttpMethod.POST, "/order/add").permitAll() //should comment in release
                .requestMatchers(HttpMethod.GET, "/order/*").permitAll() //should comment in release

                .requestMatchers(HttpMethod.GET, "/payment/token").permitAll() //should comment in release

                .requestMatchers(HttpMethod.POST, "/payment/callBack").permitAll()

                .requestMatchers(HttpMethod.POST, "/discount/*").permitAll() //should comment in release
                .requestMatchers(HttpMethod.GET, "/discount/*").permitAll() //should comment in release

                .requestMatchers(HttpMethod.GET, "/company/loadEla").permitAll()
                .requestMatchers(HttpMethod.GET, "/article/loadEla").permitAll()
                .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html")
                .permitAll()
                .anyRequest().authenticated()
        );
        http.exceptionHandling(httpSecurityExceptionHandlingConfigurer -> {
            httpSecurityExceptionHandlingConfigurer.authenticationEntryPoint(customAuthenticationEntryPoint);
        });
        http.addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);
        http.cors(Customizer.withDefaults());
        return http.build();
    }

    @Bean
    public WebMvcConfigurer corsConfigure() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedOrigins("*")
                        .allowedMethods("GET", "POST","PUT", "DELETE","UPDATE" , "OPTIONS" ,"*");
            }
        };
    }

//    @Autowired
//    Interceptor serviceInterceptor;
//
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(serviceInterceptor);
//    }
}