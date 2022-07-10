package com.education.School.controller;


import com.education.School.model.Person;
import com.education.School.repository.personRepository;
import com.education.School.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
public class dashboardController {

    private final ContactService contactService;

    @Autowired
    private personRepository personRepo;
    @Autowired
    public dashboardController(ContactService contactService){
        this.contactService = contactService;
    }

    @RequestMapping("/dashboard")
    public String displayDashboard(Model model , Authentication authentication , HttpSession session){
       Person person = personRepo.readByEmail(authentication.getName());
       model.addAttribute("username" , person.getName());
       model.addAttribute("roles" , authentication.getAuthorities().toString());
        //Logic to print the class of the Student to let him know in which class he is
        if(null!=person.getClassRoom() && null != person.getClassRoom().getName()){
            model.addAttribute("enrolledClass" , person.getClassRoom().getName());
        }
       //model.addAttribute("newMsg", contactService.getCountMsg());

       /*
       When the USER very first time logs into the app he will get redirect to dashboard ,
       so here we are maintaining the USER details in Http Session
       so that whenever we need the details about logged in user we can easily
       get the Person object from the stored session where ever we want
        */
       session.setAttribute("loggedInUser" , person);
        return "dashboard.html";

    }
}
