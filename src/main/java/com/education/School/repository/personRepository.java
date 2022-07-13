package com.education.School.repository;

import com.education.School.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface personRepository extends JpaRepository<Person, Integer > {

    Person findByEmail(String email);

    Person findByMobileNumber(String mobileNumber);

    Person readByEmail(String email);

    @Query("SELECT u FROM Person u WHERE u.verificationCode = ?1")
    public Person findByVerificationCode(String code);

}
