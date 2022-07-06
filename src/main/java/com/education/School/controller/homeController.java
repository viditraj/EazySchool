package com.education.School.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
public class homeController {

    @RequestMapping(value={"","/","home"})
    public String displayHomePage(Model model){
        model.addAttribute("username","Vidit Raj");
        return "index.html";
    }
}
