package com.education.School.repository;

import com.education.School.model.Holiday;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface holidayRepository extends CrudRepository<Holiday , String> {
/*
here <Holiday, String> is used to tell that JPA that which corresponding POJO class or TABLE
this function is going to work upon and what is the datatype of the Primary Key of that table
here Holiday = POJO class and String = datatype of field day in Holiday POJO class or holidays table
*/


}
