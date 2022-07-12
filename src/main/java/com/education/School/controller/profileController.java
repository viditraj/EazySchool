package com.education.School.controller;


import com.education.School.model.Address;
import com.education.School.model.Person;
import com.education.School.model.Profile;
import com.education.School.repository.personRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Slf4j
@Controller("profileControllerBean") // giving different bean name because HAL explorer also uses profile bean therefore there was a conflict
public class profileController {

    @Autowired
    private personRepository personRepo;

    @RequestMapping("/displayProfile")
    public ModelAndView displayProfile(Model model , HttpSession session){
        Person person = (Person) session.getAttribute("loggedInUser"); // Getting the logged in User details via Session
        Profile profile = new Profile(); // This will be used to populate person's details inside profile object

        //Populating Basic Details
        profile.setName(person.getName());
        profile.setEmail(person.getEmail());
        profile.setMobileNumber(person.getMobileNumber());

        //Populating Address Details if present
        if(person.getAddress()!= null && person.getAddress().getAddressId()>0){
            profile.setAddress1(person.getAddress().getAddress1());
            profile.setAddress2(person.getAddress().getAddress2());
            profile.setCity(person.getAddress().getCity());
            profile.setState(person.getAddress().getState());
            profile.setZipCode(person.getAddress().getZipCode());
        }
        ModelAndView modelAndView = new ModelAndView("profile.html");
        modelAndView.addObject("profile",profile);
        return modelAndView;
    }

    @RequestMapping(value = "/updateProfile", method = {RequestMethod.POST})
    public String updateProfile(@Valid @ModelAttribute("profile") Profile profile, Errors errors , HttpSession session){

        if(errors.hasErrors()){
            return "profile.html";
        }
        Person person = (Person)session.getAttribute("loggedInUser");
        person.setName(profile.getName());
        person.setEmail(profile.getEmail());
        person.setMobileNumber(profile.getMobileNumber());
        if(person.getAddress()== null || !(person.getAddress().getAddressId()>0)) {
                person.setAddress(new Address());
        }
            person.getAddress().setAddress1(profile.getAddress1());
            person.getAddress().setAddress2(profile.getAddress2());
            person.getAddress().setCity(profile.getCity());
            person.getAddress().setState(profile.getState());
            person.getAddress().setZipCode(profile.getZipCode());
            //Updating the Person's details in DB using save method of JPA
            personRepo.save(person);
            //Updating the session's details also by overwriting loggedInUser with new updated person's POJO class
            session.setAttribute("loggedInUser" , person);
            return "redirect:/displayProfile";
    }

}
