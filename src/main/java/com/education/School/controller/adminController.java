package com.education.School.controller;


import com.education.School.model.ClassRoom;
import com.education.School.model.Contact;
import com.education.School.service.ContactService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Slf4j
@Data
@Controller
@RequestMapping("/admin")
public class adminController {

    @Autowired
    private ContactService contactService;

    //METHOD TO DISPLAY MESSAGES TO ADMIN
    @GetMapping("/displayMessages")
    public ModelAndView displayMessages(Model model){

        //INVOKING findMsgsWithOpenStatus function in contactService class to get msgs from db in form of List
        List<Contact> contactMsgs = contactService.findMsgsWithOpenStatus();
        //injecting msgs into messages.html template and returning that template to display
        ModelAndView modelAndView = new ModelAndView("messages.html");
        modelAndView.addObject("contactMsgs" , contactMsgs);
        return modelAndView;
    }

    //METHOD TO DISPLAY CLASSES TO ADMIN
    @RequestMapping("/displayClasses")
    public ModelAndView displayClasses(Model model){
        ModelAndView modelAndView = new ModelAndView("classes.html");
        modelAndView.addObject("classRoom" , new ClassRoom());
        return modelAndView;
    }
}
