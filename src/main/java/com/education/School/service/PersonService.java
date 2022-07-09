package com.education.School.service;


import com.education.School.model.Person;
import com.education.School.model.Roles;
import com.education.School.repository.personRepository;
import com.education.School.repository.rolesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class PersonService {

    @Autowired
    private personRepository personRepo;

    @Autowired
    private rolesRepository rolesRepo;
    public int createNewPerson(Person person) {

        int isSaved = 0;
        Roles role = rolesRepo.getByRoleName("STUDENT");
        person.setRoles(role);
        Person alreadyRegisteredEmail = personRepo.findByEmail(person.getEmail());
        Person alreadyRegisteredPhone = personRepo.findByMobileNumber(person.getMobileNumber());
        if(null!=alreadyRegisteredEmail){
            isSaved =-1;
        }
        else if (null!=alreadyRegisteredPhone){
            isSaved =-2;
        }
        else{
            Person savedPerson = personRepo.save(person);
            if(null!=savedPerson && savedPerson.getPersonId()>0){
                isSaved = 1;
            }
        }
        return isSaved;
    }
}
