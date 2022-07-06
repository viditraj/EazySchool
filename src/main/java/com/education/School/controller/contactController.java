package com.education.School.controller;

import com.education.School.model.Contact;
import com.education.School.service.ContactService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Slf4j // Lombok annotation to give us log.info log.error functionality directly
@Controller
public class contactController {

    //contactService object
    private final ContactService contactService;


    //Injecting contactService bean in contactController
    @Autowired
    public contactController(ContactService contactService){
        this.contactService = contactService;
    }
    //due to Slf4j annotation we no more need to make object of log
    //private static Logger log = LoggerFactory.getLogger(contactController.class);

    @RequestMapping(value={"contact"})
    public String displayContactPage(){
        return "contact.html";
    }

    //Method 1 : Get input from user using RequestParam annotation
  /*   @PostMapping(value = "saveMsg")
    public ModelAndView saveMessage(@RequestParam String name , @RequestParam String mobileNum , @RequestParam String email,
                                    @RequestParam String subject, @RequestParam String message){
        log.info("Name :"+ name);
        log.info("Mobile :"+ mobileNum);
        log.info("Email :"+ email);
        log.info("Message :"+ message);

        return new ModelAndView("redirect:/contact");
    } */

    @PostMapping(value = "saveMsg")
    public ModelAndView saveMsg(Contact contact){
        //Accessing saveMessageDetails() function and passing contact object to display or save input data
        contactService.saveMessageDetails(contact);
        return new ModelAndView("redirect:/contact");
    }
}