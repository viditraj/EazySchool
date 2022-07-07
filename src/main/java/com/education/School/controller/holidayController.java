package com.education.School.controller;

import com.education.School.model.Holiday;
import com.education.School.repository.holidayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class holidayController {
    @Autowired
    private holidayRepository holiRepo;
    //METHOD 1 Accessing URL parameters using RequestParam Annotation

   /* @GetMapping(value = "/holidays")
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
    } */

    //METHOD 2 Accessing URL parameters using PathVariable Annotation

    @GetMapping("/holidays/{display}") //here {display} is the variable part of the url
    public String displayHolidays(@PathVariable String display , Model model){

        if(null!= display && display.equals("all")){
            model.addAttribute("festival",true); //Adding these to holidays.html
            model.addAttribute("federal",true);
        }
        else if(null!= display && display.equals("festival")){
            model.addAttribute("festival",true); //Adding these to holidays.html
        }
        else if(null!= display && display.equals("federal")){
            model.addAttribute("federal",true); //Adding these to holidays.html
        }


        List<Holiday> holidays = holiRepo.getHolidays();
        Holiday.Type types [] = Holiday.Type.values();
        for(Holiday.Type type : types){
            model.addAttribute(type.toString(),
                    (holidays.stream().filter(holiday -> holiday.getType().equals(type)).collect(Collectors.toList())));
        }
        return "holidays.html";
    }
}
