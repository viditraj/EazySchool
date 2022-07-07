package com.education.School.service;


import com.education.School.controller.contactController;
import com.education.School.model.Contact;
import com.education.School.repository.contactRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ContactService {

    @Autowired
    private contactRepository conRepo;
    private static Logger log = LoggerFactory.getLogger(contactController.class);

    //FUNCTION TO SAVE MESSAGES FROM USER INTO DATABASE
    public boolean saveMessageDetails(Contact contact){  //Passing the contact object we get from contactController class
        boolean isSaved = false;
        //Populating the last 4 fields
        contact.setStatus("OPEN");
        contact.setCreatedAt(LocalDateTime.now());
        contact.setCreatedBy("ANONYMOUS");
        //calling contactRepository.saveContactMsg() function to save the details in DB
        int result = conRepo.saveContactMsg(contact);
        if(result>0){
            isSaved = true;
        }
        return isSaved;
    }

    //FUNCTION TO FIND MESSAGES AND DISPLAYING THEM

    public List<Contact> findMsgsWithOpenStatus(){
        //Invoking the DATA layer repository method findMsgsWithStatus() which
        //fetches entries from database based on the status passed in parameter
        List<Contact> contactMsgs = conRepo.findMsgsWithStatus("OPEN"); //conRepo = object of contactRepository Class

        return contactMsgs;
    }

    //FUNTION TO INVOKE updateMsgStatus FUNCTION IN contactRepository class FOR CLOSING THE MSG

    public boolean updateMsgStatus(int id , String updatedBy){
        boolean isUpdated = false;
        int result = conRepo.updateMsgStatus(id , "CLOSE" , updatedBy);
        if(result>0){
            isUpdated = true;
        }
        return isUpdated;
    }
}
