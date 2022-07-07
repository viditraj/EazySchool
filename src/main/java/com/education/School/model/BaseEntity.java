package com.education.School.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BaseEntity {

    /*
    We need these 4 fields in almost all the tables/model class
    Hence instead of creating these variables again, and again
    We are creating them once and after that other classes will simply inherit them
    by extending this class
    */

    private LocalDateTime createdAt;
    private String createdBy;
    private LocalDateTime updatedAt;
    private String updatedBy;
    private String status;

}
