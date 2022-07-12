package com.education.School.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.mail.MailSendException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.mail.MessagingException;

/*
@ControllerAdvice is a specialization of the @Component annotation which allows to handle exceptions
across the whole application in one global handling component. It can be viewed as an interceptor of
exceptions thrown by methods annotated with @RequestMapping and similar.
*/
@Slf4j
@ControllerAdvice (annotations = Controller.class)
/* Using this '(annotations = Controller.class)' annotation we are restricting this error handler only to controllers
which have @Controller annotation, for our API we have @RestController annotation so this error handler will not handle its errors
We have build RestApiErrorHandler separately. */
public class globalExceptionController {
    /*
    @ExceptionHandler will register the given method for a given exception type, so that ControllerAdvice can invoke
    this method logic if a given exception type is thrown inside the web application.

    Exception.class is the parent of all the exception ie it contains all the exceptions.
    if we can to invoke the ExceptionHandler for a specific exception we can use that
    like NullPointerException.class , ARRAYINDEXOUTOFBOUNDEXCEPTION.class etc
     */


    @ExceptionHandler(Exception.class)
    public ModelAndView exceptionHandler(Exception exception){
        ModelAndView errorPage = new ModelAndView();
        errorPage.setViewName("error"); //error.html
        errorPage.addObject("errormsg", exception.getMessage());
        log.error("Error Occurred : "+exception.getMessage());
        return errorPage;
    }
    @ExceptionHandler(BadSqlGrammarException.class)
    public ModelAndView exceptionHandler(){
        ModelAndView errorPage = new ModelAndView();
        errorPage.setViewName("error"); //error.html
        errorPage.addObject("errormsg", "Its simply a Syntax Error, Chill and Check the syntax of your DB Query");
        log.error("Error Occurred : Syntax Error in SQL Query");
        return errorPage;
    }

    @ExceptionHandler({MailSendException.class, MessagingException.class})
    public void mailSendError(){
        log.error("Failed to send the mail to the user");
    }
}
