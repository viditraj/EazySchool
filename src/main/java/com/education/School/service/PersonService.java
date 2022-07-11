package com.education.School.service;


import com.education.School.model.Person;
import com.education.School.model.Roles;
import com.education.School.repository.personRepository;
import com.education.School.repository.rolesRepository;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class PersonService {

    @Autowired
    private personRepository personRepo;

    @Autowired
    private rolesRepository rolesRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;
    public int createNewPerson(Person person) {

        int isSaved = 0;
        Roles role = rolesRepo.getByRoleName("STUDENT");
        Person alreadyRegisteredEmail = personRepo.findByEmail(person.getEmail());
        Person alreadyRegisteredPhone = personRepo.findByMobileNumber(person.getMobileNumber());
        if(null!=alreadyRegisteredEmail){
            isSaved =-1;
        }
        else if (null!=alreadyRegisteredPhone){
            isSaved =-2;
        }
        else{
            person.setRoles(role);
            person.setPwd(passwordEncoder.encode(person.getPwd())); // hashing the password before storing it into DB
            person.setEnabled(false);
            String randomCode = RandomString.make(64);
            person.setVerificationCode(randomCode);
            Person savedPerson = personRepo.save(person);
            if(null!=savedPerson && savedPerson.getPersonId()>0){
                isSaved = 1;
            }
        }
        return isSaved;
    }

    public boolean verify(String verificationCode) {
        Person user = personRepo.findByVerificationCode(verificationCode);

        if (user == null || user.isEnabled()) {
            return false;
        } else {
            user.setVerificationCode(null);
            user.setEnabled(true);
            personRepo.save(user);

            return true;
        }

    }
}
