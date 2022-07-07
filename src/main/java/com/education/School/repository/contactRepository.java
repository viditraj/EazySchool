package com.education.School.repository;


import com.education.School.model.Contact;
import com.education.School.rowMapper.contactRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

//Repository annotation is used to tell Spring that this Bean will do DB related operations
@Repository
public class contactRepository {

    private final JdbcTemplate jdbcTemplate ;

    @Autowired
    public contactRepository(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    //DATABASE METHOD TO SAVE MSGS INTO DATABASE
    public int saveContactMsg(Contact contact){

        String sql ="INSERT INTO CONTACT_MSG (NAME,MOBILE_NUM,EMAIL,SUBJECT,MESSAGE,STATUS,"+
                "CREATED_AT,CREATED_BY) VALUES( ?,?,?,?,?,?,?,?);";

        return jdbcTemplate.update(sql, contact.getName(),contact.getMobileNum(),
                contact.getEmail(),contact.getSubject(),contact.getMessage(),
                contact.getStatus(),contact.getCreatedAt(),contact.getCreatedBy());
    }

    //DATABASE METHOD TO FETCH MSGS BASED ON STATUS PASSED AS PARAMETER

    public List<Contact> findMsgsWithStatus(String status){
        String sql = "SELECT * FROM CONTACT_MSG WHERE STATUS = ?";
        return jdbcTemplate.query(sql,new PreparedStatementSetter() {
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setString(1, status);
            }
        },new contactRowMapper());
    }

    //DB METHOD TO CHANGE STATUS OF THE MESSAGE
    public int updateMsgStatus(int id , String status , String updatedBy){
        String sql = "UPDATE CONTACT_MSG SET STATUS = ?,UPDATED_BY = ? , UPDATED_AT = ? WHERE CONTACT_ID = ?";
        return jdbcTemplate.update(sql,new PreparedStatementSetter(){
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setString(1,status);
                ps.setString(2,updatedBy);
                ps.setTimestamp(3,Timestamp.valueOf(LocalDateTime.now()));
                ps.setInt(4,id);
            }
        });
    }
}
