package com.jahanfoolad.jfs.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * CustomAuthenticationFilter handle the user authentication and successful authentication to generate token and refresh token
 */
@Slf4j
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    public CustomAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        log.info("username  " + username + " password : " + password);
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        return authenticationManager.authenticate(authenticationToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
//        User user = (User) authResult.getPrincipal();
//        Algorithm algorithm =  Algorithm.HMAC256(CONSTANTS.SECRET_KEY.getBytes());
//        String access_token = JWT.create()
//                .withSubject(user.getUsername())
//                .withExpiresAt(new Date(System.currentTimeMillis()+10 * 60 * 1000))
//                .withIssuer(request.getRequestURL().toString())
//                .withClaim(CONSTANTS.CLAIM_NAME , user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
//                .sign(algorithm);
//
//        String refresh_token = JWT.create()
//                .withSubject(user.getUsername())
//                .withExpiresAt(new Date(System.currentTimeMillis()+30 * 60 * 1000))
//                .withIssuer(request.getRequestURL().toString())
//                .sign(algorithm);
//
//        response.setHeader(CONSTANTS.ACCESS_TOKEN,access_token);
//        response.setHeader(CONSTANTS.REFRESH_TOKEN,refresh_token);

        Map<String, String> tokens = new HashMap<>();
//        tokens.put(CONSTANTS.ACCESS_TOKEN,access_token);
//        tokens.put(CONSTANTS.REFRESH_TOKEN,refresh_token);
        new ObjectMapper().writeValue(response.getOutputStream(), tokens);
    }
}
