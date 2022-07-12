package com.education.School.controller;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class smsController {

    @GetMapping(value = "/sendSMS")
    public ResponseEntity<String> sendSMS() {

        Twilio.init("${twillo.sid}", "1f7057ea902c670c3079e3ef6505ac09");
        Message.creator(new com.twilio.type.PhoneNumber("whatsapp:+918604633326"),
                new com.twilio.type.PhoneNumber("whatsapp:+13203359621"), "Hello from Twilio 📞").create();

        return new ResponseEntity<String>("Message sent successfully", HttpStatus.OK);
    }
}