package com.education.School.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Slf4j
@Controller
public class homeController {

    @RequestMapping(value={"","/","home"})
    public String displayHomePage(Model model){
        return "index.html";
    }

}
