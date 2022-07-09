package com.education.School.controller;


import com.education.School.model.Person;
import com.education.School.service.PersonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

@Slf4j
@Controller
@RequestMapping("public")  // whenever anyone tries to access '../public/*' path this class will be hit
public class publicController {

    @Autowired
    PersonService personService;
    @RequestMapping(value = "/register" , method = {RequestMethod.GET}) // this method will get invoked
    // if user tries to access '/public/register'
    public String displayRegisterPage(Model model){

        model.addAttribute("person" , new Person()); // Creating and adding empty Person object
        // so that details can be stored in this object
        return "register.html";
    }

    //FUNCTION TO CREATE USER
    @RequestMapping(value ="/createUser" , method = {RequestMethod.POST})
    public String createUser(@Valid @ModelAttribute("person") Person person , Errors errors , Model model){

            if(errors.hasErrors()){
                return "register.html";
            }
            int isSaved = personService.createNewPerson(person);
            if(isSaved == 1 )
            return "redirect:/login?register=true";
            else if (isSaved == -1){
                model.addAttribute("userFound" ,"User with this Email already exists. Try Logging in! ");
            }
            else if(isSaved == -2){
                model.addAttribute("userFound" ,"User with this Mobile No. already exists. Try Logging in! ");
            }
            return "register.html";
    }

}
