package com.jahanfoolad.jfs.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.jahanfoolad.jfs.JfsApplication;
import com.jahanfoolad.jfs.utils.CONSTANTS;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static java.util.Arrays.stream;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;


/*
 * this class use to controller denial-of-service  attacks
 * its not exactly controller the DDOS, but we controller the number of attempts from non android application client, if session could set
 * for particular IP address then nothing goes wrong for respected IP , but if session could not set for client after 2 request, we throws
 * the AuthorizationServiceException to stop client request
 */
@Slf4j
@Service
@Component
public class DDoSControlService {

//    @Autowired
//    UserRepo loginService;

    private LoadingCache<String, Integer> attemptsCache;

    public DDoSControlService() {
        attemptsCache = CacheBuilder.newBuilder().build(new CacheLoader<String, Integer>() {
            public Integer load(String key) {
                return 0;
            }
        });
    }

    private int numberOfAttempts(HttpServletRequest request) {
        String ipAddress = JfsApplication.getClientIP(request);
        try {
            int count = attemptsCache.get(ipAddress);
            attemptsCache.put(ipAddress, count + 1);
            return count;

        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return 0;
    }

//    public void isVerifiedClient(String frontEndAddress, HttpServletRequest request, HttpServletResponse response, List<User> users) {
//        log.info("path in token : " + request.getServletPath());
//        if (!isTrustedAgent(request, frontEndAddress)) {
//            int count = numberOfAttempts(request);
//            if (count > 2 && (!request.getServletPath().equals("/user/login") &&
//                    !request.getServletPath().equals("/user/add") &&
//                    !request.getServletPath().equals("/wallet/create") &&
//                    !request.getServletPath().equals("/user/getPassword"))) {
//                if (!isRequestValid(request, users))
//                    throw new AuthorizationServiceException(HttpStatus.FORBIDDEN.name());
//            }
//        }
//    }

    private void clearAttemptsForIp(HttpServletRequest request) {
        attemptsCache.invalidate(JfsApplication.getClientIP(request));
    }

    private boolean isTrustedAgent(HttpServletRequest request, String frontEndAddress) {

     /* For security purpose we only accept request from Browser and Android application

        curl/7.68.0
        PostmanRuntime
        Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/94.0.4606.71 Safari/537.36
        Mozilla/5.0 (Linux; Android 11; Pixel 2 XL) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/95.0.4638.50 Mobile Safari/537.36

     */
        String userAgent = request.getHeader("User-Agent");
        log.info("user agent : " + userAgent);
        if (!JfsApplication.getClientIP(request).equalsIgnoreCase(frontEndAddress))
            if (userAgent != null && !userAgent.contains("okhttp"))
                return false;
        return true;
    }

//    private boolean isRequestValid(HttpServletRequest request, List<Users> users) {
//        String userName = tokenVerification(request);
//        return users.stream().anyMatch(userInfo -> userInfo.getCellNumber().equalsIgnoreCase(userName));
//    }

    public String tokenVerification(HttpServletRequest request) {
        if (request.getHeader(AUTHORIZATION) != null && !request.getServletPath().equals("/user/refresh")) {
            String authHeader = request.getHeader(AUTHORIZATION);
            log.info("path in token : " + request.getServletPath());
            if (authHeader != null && authHeader.startsWith("Bearer") && authHeader.length() > 8) {
                try {
                    String token = authHeader.substring("Bearer ".length());
                    Algorithm algorithm = Algorithm.HMAC256(CONSTANTS.SECRET_KEY.getBytes());
                    JWTVerifier jwtVerifier = JWT.require(algorithm).build();
                    DecodedJWT decodedJWT = jwtVerifier.verify(token);

                    String username = decodedJWT.getSubject();
                    String password = decodedJWT.getAudience().get(0);
                    String[] roles = decodedJWT.getClaim(CONSTANTS.CLAIM_NAME).asArray(String.class);

                    Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
                    stream(roles).forEach(role -> {
                        authorities.add(new SimpleGrantedAuthority(role));
                    });

                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(username, password, authorities);
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    log.info("token user name : " + username);

                    return username;

                } catch (Exception e) {
                    log.error("the token AuthenticationException... " + e.toString());
                }
            }
        }
        return null;
    }

    public void setAuthorizationForContext(String recToken){
        String authHeader = recToken ; //request.getHeader(AUTHORIZATION);
        if (authHeader != null && authHeader.startsWith("Bearer") && authHeader.length() > 8) {
            try {
                String token = authHeader.substring("Bearer ".length());
                Algorithm algorithm = Algorithm.HMAC256(CONSTANTS.SECRET_KEY.getBytes());
                JWTVerifier jwtVerifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = jwtVerifier.verify(token);
                String username = decodedJWT.getSubject();
                String password = decodedJWT.getAudience().get(0);
                String[] roles = decodedJWT.getClaim(CONSTANTS.CLAIM_NAME).asArray(String.class);
                Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
                stream(roles).forEach(role -> {
                    authorities.add(new SimpleGrantedAuthority(role));
                });
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(username, password, authorities);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (Exception exc) {
                exc.printStackTrace();
            }
        } else {
            throw new RuntimeException("Refresh token is missing");
        }
    }

//    private Users fetchUserName(String token) {
//        Algorithm algorithm = Algorithm.HMAC256(CONSTANTS.SECRET_KEY.getBytes());
//        JWTVerifier jwtVerifier = JWT.require(algorithm).build();
//        DecodedJWT decodedJWT = jwtVerifier.verify(token);
//        String username = decodedJWT.getSubject();
//        return loginService.findByUserNameIgnoreCase(username);
//    }

    //    public UserGrantedModel isUserGranted(String token, Long bookId) {
//        UserGrantedModel userGrantedModel = new UserGrantedModel();
//        Users user = fetchUserName(token);
//        userGrantedModel.setGranted(user.getBooks().stream().anyMatch(book -> bookId.equals(book.getId())));
//        userGrantedModel.setUserName(user.getUserName());
//        return userGrantedModel;
//    }
    public class UserGrantedModel {
        boolean isGranted;
        String userName;

        public boolean isGranted() {
            return isGranted;
        }

        public void setGranted(boolean granted) {
            isGranted = granted;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }
    }
}
