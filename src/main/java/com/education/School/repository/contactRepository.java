package com.education.School.repository;


import com.education.School.model.Contact;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

//Repository annotation is used to tell Spring that this Bean will do DB related operations
@Repository
public interface contactRepository extends CrudRepository<Contact , Integer> {


    /*
    To implement selects with custom where conditions we can make methods with such naming conventions which
    will tell Spring JPA about what we want to find
    Eg: findByAge(String Age) -> if we implement this method then Spring JPA is smart enough to derive
    SQL select statements with desired where conditions which it will get from the method name
    In our case Spring JPA will automatically select rows which will have age field value = value passed as parameter
    */

    //Abstract method to find msg with given status value passed as the parameter
    List<Contact> findByStatus(String status); //this will tell JPA to find all the contact msgs whose status = status value passed in parameter

    Page<Contact> findByStatus(String status, Pageable pageable); //this will allow JPA to have pagination capability
}


















/************** IMPLEMENTATION BEFORE JAP ***************/

 /* private final JdbcTemplate jdbcTemplate ;

    @Autowired
    public contactRepository(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    //DATABASE METHOD TO SAVE MSGS INTO DATABASE
    public int saveContactMsg(Contact contact){

        String sql ="insert into contact_msg (NAME,MOBILE_NUM,EMAIL,SUBJECT,MESSAGE,STATUS,"+
                "CREATED_AT,CREATED_BY) values( ?,?,?,?,?,?,?,?);";

        return jdbcTemplate.update(sql, contact.getName(),contact.getMobileNum(),
                contact.getEmail(),contact.getSubject(),contact.getMessage(),
                contact.getStatus(),contact.getCreatedAt(),contact.getCreatedBy());
    }

    //DATABASE METHOD TO FETCH MSGS BASED ON STATUS PASSED AS PARAMETER

    public List<Contact> findMsgsWithStatus(String status){
        String sql = "select * from contact_msg where STATUS = ?";
        return jdbcTemplate.query(sql,new PreparedStatementSetter() {
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setString(1, status);
            }
        },new contactRowMapper());
    }

    //DB METHOD TO CHANGE STATUS OF THE MESSAGE
    public int updateMsgStatus(int id , String status , String updatedBy){
        String sql = "update contact_msg set STATUS = ?,UPDATED_BY = ? , UPDATED_AT = ? where CONTACT_ID = ?";
        return jdbcTemplate.update(sql,new PreparedStatementSetter(){
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setString(1,status);
                ps.setString(2,updatedBy);
                ps.setTimestamp(3,Timestamp.valueOf(LocalDateTime.now()));
                ps.setInt(4,id);
            }
        });
    }

    */