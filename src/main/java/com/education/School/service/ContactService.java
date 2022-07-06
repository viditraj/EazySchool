package com.education.School.service;


import com.education.School.controller.contactController;
import com.education.School.model.Contact;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ContactService {
    private static Logger log = LoggerFactory.getLogger(contactController.class);

    public boolean saveMessageDetails(Contact contact){
        boolean isSaved = true;
        log.info(contact.toString());
        return isSaved;
    }

}
