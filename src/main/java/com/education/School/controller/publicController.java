package com.education.School.controller;


import com.education.School.model.Person;
import com.education.School.service.PersonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;

@Slf4j
@Controller
@RequestMapping("public")  // whenever anyone tries to access '../public/*' path this class will be hit
public class publicController {

    @Autowired
    PersonService personService;

    @Autowired
    private JavaMailSender mailSender;
    @RequestMapping(value = "/register" , method = {RequestMethod.GET}) // this method will get invoked
    // if user tries to access '/public/register'
    public String displayRegisterPage(Model model){

        model.addAttribute("person" , new Person()); // Creating and adding empty Person object
        // so that details can be stored in this object
        return "register.html";
    }

    //FUNCTION TO CREATE USER
    @RequestMapping(value ="/createUser" , method = {RequestMethod.POST} )
    public String createUser(@Valid @ModelAttribute("person") Person person , Errors errors , HttpServletRequest request, Model model) throws MessagingException, UnsupportedEncodingException {

            if(errors.hasErrors()){
                return "register.html";
            }
            int isSaved = personService.createNewPerson(person);
            if(isSaved == 1 ){
                String siteURL = request.getRequestURL().toString();
                siteURL = siteURL.replace(request.getServletPath(),"");
                sendVerificationEmail(person , siteURL);
                return "redirect:/login?register=true";
            }

            else if (isSaved == -1){
                model.addAttribute("userFound" ,"User with this Email already exists. Try Logging in! ");
            }
            else if(isSaved == -2){
                model.addAttribute("userFound" ,"User with this Mobile No. already exists. Try Logging in! ");
            }
            return "register.html";
    }

    @RequestMapping(value="/verify",method = {RequestMethod.GET})
    public String verifyMail(@RequestParam(value="code")String code){
        ModelAndView modelAndView = new ModelAndView("register.html");
        if(personService.verify(code) == true){
            return "redirect:/login?verify=true";
        }
        else
            return "register.html";
    }


    private void sendVerificationEmail(Person user,String siteURL) throws UnsupportedEncodingException, MessagingException {
        String toAddress = user.getEmail();
        String fromAddress = "viditraj20@gmail.com";
        String senderName = "XYZ School";
        String subject = "Please verify your registration";
        String content = "Hi [[name]],<br>"
                +"We just need to verify your email address before you can access our App.<br>"
                + "Please click the link below to verify your registration:<br>"
                + "<h3><a href=\"[[URL]]\" target=\"_self\">VERIFY</a></h3>"
                + "Thanks,<br>"
                + "XYZ School.";

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom(fromAddress, senderName);
        helper.setTo(toAddress);
        helper.setSubject(subject);

        content = content.replace("[[name]]", user.getName());
        String verifyURL = siteURL + "/public/verify?code=" + user.getVerificationCode();

        content = content.replace("[[URL]]", verifyURL);

        helper.setText(content, true);

        mailSender.send(message);

    }

}
