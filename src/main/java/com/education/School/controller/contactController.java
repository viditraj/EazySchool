package com.education.School.controller;

import com.education.School.model.Contact;
import com.education.School.service.ContactService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

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
    public String displayContactPage(Model model){
        model.addAttribute("contact" , new Contact()); // This tells thymeleaf that this attribute contains data for contact
        //class which runs all the data validations mentioned in contact POJO class. Everytime user lands on contact page a new contact POJO class is created
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
    //When the user submits the form this route is hit. Validations are performed as we are using @VALID annotation
    //on the POJO object as Defined by @ModelAttribute and if there are any errors then we catch them in errors object of Errors
    //If there are errors don't save the data simply log the errors otherwise save the data
    public String saveMsg(@Valid @ModelAttribute("contact") Contact contact , Errors errors , RedirectAttributes rediAttri){
        if(errors.hasErrors()){
            log.error("Contact form validations failed due to : "+ errors.toString());
            return "contact.html";
        }

        //Accessing saveMessageDetails() function and passing contact object to display or save input data into DB
        contactService.saveMessageDetails(contact);
        //To display success message
        rediAttri.addFlashAttribute("success" ,"Submitted Successfully" );
        return "redirect:/contact";
    }

    //Controller function to retrieve msgs from DB and show them to ADMIN
    @RequestMapping("/displayMessages")
    public ModelAndView displayMessages(Model model){

        //INVOKING findMsgsWithOpenStatus function in contactService class to get msgs from db in form of List
        List<Contact> contactMsgs = contactService.findMsgsWithOpenStatus();
        //injecting msgs into messages.html template and returning that template to display
        ModelAndView modelAndView = new ModelAndView("messages.html");
        modelAndView.addObject("contactMsgs" , contactMsgs);
        return modelAndView;
    }

    //Controller function to close the msg and update the database that a particular msg with given id is closed

    @GetMapping("/closeMsg")
    public String closeMsg(@RequestParam int id){
        //Getting the Authentication details of the users who is closing the msg to update the updated by details
            contactService.updateMsgStatus(id);
            return"redirect:/displayMessages";
    }
}