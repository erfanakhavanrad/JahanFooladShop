package com.jahanfoolad.jfs.service.impl;

import com.jahanfoolad.jfs.JfsApplication;
import com.jahanfoolad.jfs.domain.*;
import com.jahanfoolad.jfs.jpaRepository.PersonRepository;
import com.jahanfoolad.jfs.security.CustomAuthenticationException;
import com.jahanfoolad.jfs.security.EncyDecyECB;
import com.jahanfoolad.jfs.security.SecurityService;
import com.jahanfoolad.jfs.service.RoleService;
import com.jahanfoolad.jfs.utils.LoginAttemptService;
import com.jahanfoolad.jfs.utils.PasswordSendFactory;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataAccessException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.naming.AuthenticationException;
import java.util.*;

@Component
public class PersonService<P extends Person> implements UserDetailsService {

    @Autowired
    PasswordEncoder passwordEncode;

    @Autowired
    PersonRepository personRepository;

    @Autowired
    ResponseModel responseModel;

    @Autowired
    LoginAttemptService loginAttemptService;

    @Autowired
    SecurityService securityService;

    @Autowired
    PasswordSendFactory passwordSendFactory;

    @Autowired
    RoleService roleService;

    @Autowired
    EmailServices emailServices;

    @Value("${SUCCESS_RESULT}")
    int success;

    @Value("${FAIL_RESULT}")
    int fail;

    String generatedPassword;

    @Resource(name = "faMessageSource")
    private MessageSource faMessageSource;

    public P save(P person) throws Exception {
        String password = getPassword();
        person.setPassword(password);
        P savedPerson = personRepository.save(person);
        passwordSendFactory.send(savedPerson, RegisterType.PHONE);
        return person;
    }


    public ResponseModel login(P person, HttpServletRequest request) throws Exception {
//        String decryptPass = EncyDecyECB.decrypt(person.getPassword());// uncomment after ui get key
//        System.out.println("decryptPass password : " + decryptPass);// uncomment after ui get key
        P authedPerson = userAuthentication(person.getUserName(), person.getPassword(), request);// and this will change to decryptPass
        if (authedPerson != null) {
            authedPerson.setLastLoginDate(new Date());
            P saved = personRepository.save(authedPerson);
            return setUserData(saved);
        } else {
            throw new AuthenticationException(faMessageSource.getMessage("WRONG_USER_PASS", null, Locale.ENGLISH));
        }
    }

    public void updateImageUrl(String imgUrl, long uid) throws Exception{
        personRepository.updateImageUrl(uid, imgUrl);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, DataAccessException {
        P foundPerson = (P) personRepository.findByUserNameIgnoreCase(username);
        List<Privilege> privileges = new ArrayList<>();
        if (foundPerson == null)
            throw new UsernameNotFoundException(faMessageSource.getMessage("USER_NOT_EXISTS", null, Locale.ENGLISH));
        foundPerson.getRole().stream().forEach(role -> {
            role.getPrivileges().forEach(privilege -> {
                privileges.add(privilege);
            });
        });
        return new org.springframework.security.core.userdetails.User(foundPerson.getUserName(), foundPerson.getPassword(), privileges);
    }

    public P findByUserName(String userName){
        return (P) personRepository.findByUserNameIgnoreCase(userName);
    }

    private P userAuthentication(String userName, String password, HttpServletRequest request) throws CustomAuthenticationException {
        final String xfHeader = request.getHeader("X-Forwarded-For");
        String ip = JfsApplication.getClientIP(request);
        if (loginAttemptService.isBlocked(ip))
            throw new CustomAuthenticationException("Too many attempts , your IP has been blocked for 5 min");

        P user = (P) personRepository.findByUserNameIgnoreCase(userName);
        if (user != null) {
            if (!user.isActive())
                throw new CustomAuthenticationException("your account has been deactivated , please call administrator");
            boolean isPasswordMatch = JfsApplication.bCryptPasswordEncoder.matches(password, user.getPassword());
            if (isPasswordMatch) {
                if (xfHeader == null)
                    loginAttemptService.loginSucceeded(request.getRemoteAddr());
                else
                    loginAttemptService.loginSucceeded(xfHeader.split(",")[0]);
                return user;
            } else
                return loginFail(xfHeader, request);
        } else
            return loginFail(xfHeader, request);
    }

    private String passwordGenerator() {
        return String.format("%06d", new Random().nextInt(1000000));
    }

    private String getPassword() {
        generatedPassword = passwordGenerator();
        System.out.println(" gen pass " + generatedPassword + " enc " + EncyDecyECB.encrypt(generatedPassword));
        return JfsApplication.bCryptPasswordEncoder.encode(generatedPassword);
    }

    private ResponseModel setUserData(P user) throws Exception {
        List tokens = new ArrayList();
        tokens.add(securityService.createTokenByUserPasswordAuthentication(user.getUserName()));
        responseModel.setContents(tokens);
        responseModel.setContent(user);
        responseModel.setResult(success);
        return responseModel;
    }


    @PreAuthorize("hasPermission(#id,'User', 'delete')")
    public void changeActivation(Long id, boolean active) throws Exception {
        personRepository.updateActivation(id, active);
    }


    public void changeIsAuthorizationFlag(List<Person> people, boolean isAuthChanged) {
        people.stream().forEach(user -> {
            user.setIsAuthorizationChanged(isAuthChanged);
        });
        personRepository.saveAll(people);
    }

    public void changeIsAuthorizationFlag(Person person, boolean isAuthChanged) {
        person.setIsAuthorizationChanged(isAuthChanged);
        personRepository.save(person);
    }


    private Person setRole(Person users) {
        List<Role> roles =new ArrayList<>();
        users.getRole().forEach(role -> {
            try {
                roles.add(roleService.getRoleById(role.getId()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        users.setRole(roles);
        return users;
    }

    private P loginFail(String xfHeader, HttpServletRequest request) {
        if (xfHeader == null)
            loginAttemptService.loginFailed(request.getRemoteAddr());
        else
            loginAttemptService.loginFailed(xfHeader.split(",")[0]);
        return null;
    }
    public List<Person> findByRole(Role role) {
        return personRepository.findAllByRole(role);
    }
}
