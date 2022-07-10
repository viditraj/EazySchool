package com.education.School.controller;


import com.education.School.model.ClassRoom;
import com.education.School.model.Person;
import com.education.School.repository.classRoomRepository;
import com.education.School.repository.coursesRepository;
import com.education.School.repository.personRepository;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Slf4j
@Controller
@RequestMapping("/student")
public class studentController {

    @Autowired
    private classRoomRepository classRepo;

    @Autowired
    private personRepository personRepo;

    @Autowired
    private coursesRepository coursesRepo;

    @GetMapping("/displayCourses")
    public ModelAndView displayCourses(Model model , HttpSession session){
        Person person = (Person) session.getAttribute("loggedInUser");
        ModelAndView modelAndView = new ModelAndView("courses_enrolled.html");
        modelAndView.addObject("person", person);
        return modelAndView;
    }

}
