package com.education.School.controller;

import com.education.School.model.Holiday;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class holidayController {

    @GetMapping(value = "/holidays")

    public String displayHolidays(@RequestParam(required = false) boolean festival, //Accessing URL parameters
                                  @RequestParam(required = false) boolean federal, Model model){

        model.addAttribute("festival",festival); //Adding these to holidays.html
        model.addAttribute("federal",federal);
        List<Holiday> holidays = Arrays.asList(
          new Holiday(" Jan 1" , "New Year's Day" , Holiday.Type.FESTIVAL),
          new Holiday("Jan 26" , "Republic Day" , Holiday.Type.FEDERAL),
          new Holiday("March 12" , "Holi" , Holiday.Type.FESTIVAL)
                );
        Holiday.Type types [] = Holiday.Type.values();
        for(Holiday.Type type : types){
            model.addAttribute(type.toString(),
                    (holidays.stream().filter(holiday -> holiday.getType().equals(type)).collect(Collectors.toList())));
        }
        return "holidays.html";
    }
}
