package com.education.School.model;


import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

//Contact bean class
@Data
public class Contact {
    //Jakarta Validation is used to do the server side validations of user field data
    //@NotBlank: This annotation is used to validate that the input field is not blank
    //@Size: This annotation is used to ensure that field is having that amount of characters
    //@Pattern: It is used to validate the field based on regex expression
    //@Email : It ensures that correct email address is provided in given field
    @NotBlank(message = "Name must not be blank")
    @Size(min=3, message= "Name must be at least 3 characters long")
    private String name;
    @NotBlank(message = "Mobile no. must not be blank")
    @Pattern(regexp = "(^$|[0-9]{10})", message= "Mobile no. must be of 10 digits")
    private String mobileNum;
    @NotBlank(message = "Email must not be blank")
    @Email(message= "Please provide a valid email address")
    private String email;
    @NotBlank(message = "Subject must not be blank")
    @Size(min=5, message= "Subject must be at least 5 characters long")
    private String subject;
    @NotBlank(message = "Message must not be blank")
    @Size(min=10, message= "Message must be at least 10 characters long")
    private String message;

    }
