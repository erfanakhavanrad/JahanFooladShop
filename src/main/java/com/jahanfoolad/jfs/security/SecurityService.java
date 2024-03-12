package com.jahanfoolad.jfs.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.jahanfoolad.jfs.domain.Person;
import com.jahanfoolad.jfs.domain.ResponseModel;
import com.jahanfoolad.jfs.domain.Role;
import com.jahanfoolad.jfs.service.impl.PersonService;
import com.jahanfoolad.jfs.utils.CONSTANTS;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;


@Component
public class SecurityService {

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    ResponseModel responseModel;

    @Autowired
    PersonService userService;

    @Value("${SUCCESS_RESULT}")
    int success;

    @Value("${FAIL_RESULT}")
    int fail;

    @Resource(name = "faMessageSource")
    private MessageSource faMessageSource;

    public SecurityModel createTokenByUserPasswordAuthentication(String userName) {
        final String token = generateToken(userDetailsService.loadUserByUsername(userName));
        final String refreshToken = refreshToken(userDetailsService.loadUserByUsername(userName));
        userService.changeIsAuthorizationFlag(userService.findByUserName(userName), false);
        System.out.println("Generated token : in SecurityService class : " + refreshToken);
        return new SecurityModel(token, refreshToken);
    }

    public String generateToken(UserDetails user) {

        System.out.println("token for user " + user.getUsername() + " generated at : " + new Date());
        Algorithm algorithm = Algorithm.HMAC256(CONSTANTS.SECRET_KEY.getBytes());
        String access_token = JWT.create()
                .withSubject(user.getUsername())
                .withAudience(user.getPassword())
                .withExpiresAt(new Date(System.currentTimeMillis() + CONSTANTS.ACCESS_TOKEN_EXPIRATION * 60 * 1000))
                .withClaim(CONSTANTS.CLAIM_NAME, user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .sign(algorithm);
        return access_token;
    }

    public String refreshToken(UserDetails user) {
        Algorithm algorithm = Algorithm.HMAC256(CONSTANTS.SECRET_KEY.getBytes());
        String refresh_token = JWT.create()
                .withSubject(user.getUsername())
                .withAudience(user.getPassword())
                .withClaim(CONSTANTS.CLAIM_NAME, user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .withExpiresAt(new Date(System.currentTimeMillis() + CONSTANTS.REFRESH_TOKEN_EXPIRATION * 60 * 1000))
                .sign(algorithm);
        return refresh_token;
    }

    public ResponseModel refreshToken(HttpServletRequest request, HttpServletResponse response) {
        responseModel.clear();
        String authHeader = request.getHeader(AUTHORIZATION);
        if (authHeader != null && authHeader.startsWith("Bearer") && authHeader.length() > 8) {
            try {
                String token = authHeader.substring("Bearer ".length());
                Algorithm algorithm = Algorithm.HMAC256(CONSTANTS.SECRET_KEY.getBytes());
                JWTVerifier jwtVerifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = jwtVerifier.verify(token);
                String username = decodedJWT.getSubject();
                String password = decodedJWT.getAudience().get(0);
                String[] roles = decodedJWT.getClaim(CONSTANTS.CLAIM_NAME).asArray(String.class);


                String access_token = JWT.create()
                        .withSubject(username)
                        .withAudience(password)
                        .withExpiresAt(new Date(System.currentTimeMillis() + CONSTANTS.ACCESS_TOKEN_EXPIRATION * 60 * 1000))
                        .withIssuer(request.getRequestURL().toString())
                        .withClaim(CONSTANTS.CLAIM_NAME, stream(roles).collect(Collectors.toList()))
                        .sign(algorithm);

                String refresh_token = JWT.create()
                        .withSubject(username)
                        .withAudience(password)
                        .withExpiresAt(new Date(System.currentTimeMillis() + CONSTANTS.REFRESH_TOKEN_EXPIRATION * 60 * 1000))
                        .withIssuer(request.getRequestURL().toString())
                        .withClaim(CONSTANTS.CLAIM_NAME, stream(roles).collect(Collectors.toList()))
                        .sign(algorithm);

                response.setHeader(CONSTANTS.ACCESS_TOKEN, access_token);
                response.setHeader(CONSTANTS.REFRESH_TOKEN, refresh_token);

                Map<String, String> tokens = new HashMap<>();
                tokens.put(CONSTANTS.ACCESS_TOKEN, access_token);
                tokens.put(CONSTANTS.REFRESH_TOKEN, refresh_token);

                System.out.println("refresh token : " + refresh_token + " access token : " + access_token);
                SecurityModel securityModel = new SecurityModel(access_token, refresh_token);

                responseModel.setResult(success);
                responseModel.setError(null);
                responseModel.setSystemError(null);
                responseModel.setContent(securityModel);

            } catch (Exception exc) {
                exc.printStackTrace();
                responseModel.setResult(fail);
                responseModel.setError(null);
                responseModel.setSystemError(exc.toString());
                responseModel.setContent(null);

            }
        } else {
            throw new RuntimeException("Refresh token is missing");
        }
        return responseModel;
    }

    public Person getUserByToken(String recToken) {
        responseModel.clear();
        String authHeader = recToken; //request.getHeader(AUTHORIZATION);
        if (authHeader != null && authHeader.startsWith("Bearer") && authHeader.length() > 8) {
            try {
                String token = authHeader.substring("Bearer ".length());
                Algorithm algorithm = Algorithm.HMAC256(CONSTANTS.SECRET_KEY.getBytes());
                JWTVerifier jwtVerifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = jwtVerifier.verify(token);
                String username = decodedJWT.getSubject();
                return userService.findByUserName(username);
            } catch (TokenExpiredException ex) {
                ex.printStackTrace();
                return null;

            } catch (Exception exc) {
                exc.printStackTrace();
                return null;
            }
        } else {
            throw new RuntimeException("Refresh token is missing");
        }
    }

    public ResponseModel getUserByToken(HttpServletRequest request) {
        responseModel.clear();
        String authHeader = request.getHeader(AUTHORIZATION);
        if (authHeader != null && authHeader.startsWith("Bearer") && authHeader.length() > 8) {
            try {
                String token = authHeader.substring("Bearer ".length());
                Algorithm algorithm = Algorithm.HMAC256(CONSTANTS.SECRET_KEY.getBytes());
                JWTVerifier jwtVerifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = jwtVerifier.verify(token);
                String username = decodedJWT.getSubject();

                responseModel.setResult(success);
                responseModel.setContent(userService.findByUserName(username));

            } catch (TokenExpiredException ex) {
                ex.printStackTrace();
                responseModel.setResult(fail);
                responseModel.setError(faMessageSource.getMessage("TOKEN_NOT_VALID", null, Locale.ENGLISH));
                responseModel.setSystemError(ex.toString());
                responseModel.setContent(null);

            } catch (Exception exc) {
                exc.printStackTrace();
                responseModel.setResult(fail);
                responseModel.setError(faMessageSource.getMessage("TOKEN_NOT_VALID", null, Locale.ENGLISH));
                responseModel.setSystemError(exc.toString());
                responseModel.setContent(null);
            }
        } else {
            throw new RuntimeException("Refresh token is missing");
        }
        return responseModel;
    }

//    @Async
//    public Future<ResponseModel> getUserByTokenAsync(HttpServletRequest request) {
//        responseModel.clear();
//        String authHeader = request.getHeader(AUTHORIZATION);
//        if (authHeader != null && authHeader.startsWith("Bearer") && authHeader.length() > 8) {
//            try {
//                String token = authHeader.substring("Bearer ".length());
//                Algorithm algorithm = Algorithm.HMAC256(CONSTANTS.SECRET_KEY.getBytes());
//                JWTVerifier jwtVerifier = JWT.require(algorithm).build();
//                DecodedJWT decodedJWT = jwtVerifier.verify(token);
//                String username = decodedJWT.getSubject();
//
//                responseModel.setResult(success);
//                responseModel.setContent(userService.findByUserName(username));
//
//            } catch (TokenExpiredException ex) {
//                ex.printStackTrace();
//                responseModel.setResult(fail);
//                responseModel.setError(faMessageSource.getMessage("TOKEN_NOT_VALID" , null , Locale.ENGLISH));
//                responseModel.setSystemError(ex.toString());
//                responseModel.setContent(null);
//
//            } catch (Exception exc) {
//                exc.printStackTrace();
//                responseModel.setResult(fail);
//                responseModel.setError(faMessageSource.getMessage("UNKNOWN_TRANSACTION_ERROR" , null , Locale.ENGLISH));
//                responseModel.setSystemError(exc.toString());
//                responseModel.setContent(null);
//
//            }
//        } else {
//            throw new RuntimeException("Refresh token is missing");
//        }
//        return new AsyncResult<ResponseModel>(responseModel);
//    }

    public void usersAuthorizationChanged(Role role) {
        List<Person> users = userService.findByRole(role);
        userService.changeIsAuthorizationFlag(users, true);
    }

}
