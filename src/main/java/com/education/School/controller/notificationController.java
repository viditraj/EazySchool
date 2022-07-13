package com.education.School.controller;


import com.education.School.model.Holiday;
import com.education.School.model.Notification;
import com.education.School.repository.notificationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Slf4j
@Controller
public class notificationController {

    @Autowired
    private notificationRepository notificationRepo;

    @GetMapping("/notifications")
    public ModelAndView notification(Model model){
        ModelAndView modelAndView = new ModelAndView("notifications.html");
        Iterable<Notification> notifications = notificationRepo.findAll();
        List<Notification> notificationList = StreamSupport
                .stream(notifications.spliterator(),false)
                .collect(Collectors.toList());
        modelAndView.addObject("notifications",notificationList);
        return modelAndView;
    }

}
