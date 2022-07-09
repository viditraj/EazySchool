package com.education.School.security;

import com.education.School.model.Person;
import com.education.School.model.Roles;
import com.education.School.repository.personRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component
public class userNamePwdAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private personRepository personRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication){
        //getting current user's details from authentication object
        String email = authentication.getName();
        String pwd = authentication.getCredentials().toString();
        Person person = personRepo.readByEmail(email); // We only search person by email and dont include password
        //during searching in DB for security purpose.
        if(null!=person && person.getPersonId()>0 &&
                passwordEncoder.matches(pwd,person.getPwd())){ //matching the user entered hashed password with DB stored hashed password
            return new UsernamePasswordAuthenticationToken(
                    email,null,getGrantedAuthorities(person.getRoles())
            );
        }
        else {
            throw new BadCredentialsException("Invalid Credentials!!");
        }

    }

    private List<GrantedAuthority> getGrantedAuthorities(Roles roles) {
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_"+roles.getRoleName()));
        return grantedAuthorities;
    }

    public boolean supports(Class<?> authentication){
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
