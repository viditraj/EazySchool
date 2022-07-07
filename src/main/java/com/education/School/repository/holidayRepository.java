package com.education.School.repository;


import com.education.School.model.Holiday;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class holidayRepository {

    private final JdbcTemplate jdbcTemplate ;

    @Autowired
    public holidayRepository(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }
    public List<Holiday> getHolidays(){
        String sql ="SELECT * FROM HOLIDAYS";
        var rowMapper = BeanPropertyRowMapper.newInstance(Holiday.class);
        //When FIELD NAME and COL NAMES matches in class and DB then no need to make custom rowmapper
        //as mapping will be done automatically.
        //but we need to create an instance of the rowmapper with the class we want to map rows with
        return jdbcTemplate.query(sql,rowMapper);
    }
}
