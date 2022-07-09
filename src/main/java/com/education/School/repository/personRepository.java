package com.education.School.repository;

import com.education.School.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface personRepository extends JpaRepository<Person, Integer > {

    Person findByEmail(String email);

    Person findByMobileNumber(String mobileNumber);
}
