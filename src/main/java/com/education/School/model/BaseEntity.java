package com.education.School.model;

import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Data
@MappedSuperclass // this annotation is used to let JPA know about these fields so that it considers these fields also
@EntityListeners(AuditingEntityListener.class) // This annotation is used to enable auto auditing of fields like createdBy, createdAt etc...
public class BaseEntity {

    /*
    We need these 4 fields in almost all the tables/model class
    Hence instead of creating these variables again, and again
    We are creating them once and after that other classes will simply inherit them
    by extending this class
    */

    @CreatedDate     //annotation to tell Spring to automatically populate create time in this field
    @Column(updatable = false) //updatable = false to tell JPA that this field must only be populated at create time and should not be updated later
    private LocalDateTime createdAt;

    @CreatedBy     //annotation to tell Spring to automatically populate created by in this field
    @Column(updatable = false)
    private String createdBy;

    @LastModifiedDate
    @Column(insertable = false)  // This entry can only be updated
    private LocalDateTime updatedAt;

    @LastModifiedBy
    @Column(insertable = false)
    private String updatedBy;

}
