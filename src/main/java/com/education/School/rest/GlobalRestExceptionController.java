package com.education.School.rest;

import com.education.School.model.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/*
Exception Handling class to handle exception related to API
 */

@Slf4j
@RestControllerAdvice(annotations = RestController.class)
@Order(1)  //As we have 2 Global ErrorHandlers so SPRING was picking handlers randomly, that's why we have used @Order
//annotation and set it to 1 to give this handler priority
public class GlobalRestExceptionController extends ResponseEntityExceptionHandler {

    //EXCEPTION HANDLER FOR BAD REQUEST
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex , HttpHeaders headers,
                                                                  HttpStatus status , WebRequest request){

        Response response = new Response(status.toString(), ex.getBindingResult().toString());
        return  new ResponseEntity(response,HttpStatus.BAD_REQUEST);
    }


    //EXCEPTION HANDLER FOR EXCEPTIONS
    @ExceptionHandler({Exception.class})
    public ResponseEntity<Response> exceptionHandler(Exception exception){

        Response response = new Response("500", exception.getMessage());

        return new ResponseEntity(response , HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
