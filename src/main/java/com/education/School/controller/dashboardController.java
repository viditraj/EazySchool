package com.education.School.controller;


import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class dashboardController {

    @RequestMapping("/dashboard")
    public String displayDashboard(Model model , Authentication authentication){
       model.addAttribute("username" , authentication.getName());
       model.addAttribute("roles" , authentication.getAuthorities().toString());
        //throw new RuntimeException("It's been a bad day for us");
        return "dashboard.html";
    }
}
