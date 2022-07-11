package com.education.School.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
Response class to create Response object inorder to sent http response after getting POST request
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Response {

    private String statusCode;
    private String statusMsg;
}
