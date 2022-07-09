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
import java.util.Optional;

@Service
public class ContactService {

    @Autowired
    private contactRepository conRepo;
    private static Logger log = LoggerFactory.getLogger(contactController.class);

    public int getCountMsg() {
        int result =0;
        List<Contact> newContactMsg = conRepo.findByStatus("OPEN");
        result = newContactMsg.size();
        return result;
    }

    //FUNCTION TO SAVE MESSAGES FROM USER INTO DATABASE
    public boolean saveMessageDetails(Contact contact){  //Passing the contact object we get from contactController class
        boolean isSaved = false;
        //Populating the last 4 fields
        contact.setStatus("OPEN");
       // contact.setCreatedAt(LocalDateTime.now()); --no need of this now as we are auditing these fields via auditor
       // contact.setCreatedBy("ANONYMOUS");  --no need of this now as we are auditing these fields via auditor
        //calling contactRepository.saveContactMsg() function to save the details in DB
        //int result = conRepo.saveContactMsg(contact); --before JPA
        Contact savedContact = conRepo.save(contact); //--after JPA .save() returns the same object it is saving in DB
        if(null!=savedContact && savedContact.getContactId()>0){
            isSaved = true;
        }
        return isSaved;
    }

    //FUNCTION TO FIND MESSAGES AND DISPLAYING THEM

    public List<Contact> findMsgsWithOpenStatus(){
        //Invoking the DATA layer repository method findMsgsWithStatus() which
        //fetches entries from database based on the status passed in parameter
       //  List<Contact> contactMsgs = conRepo.findMsgsWithStatus("OPEN"); //conRepo = object of contactRepository Class --> before JPA
        List<Contact> contactMsgs = conRepo.findByStatus("OPEN");
        //--> after implementing JPA we have simply created findByStatus abstract method and all the work is handled by JPA
        return contactMsgs;
    }

    //FUNTION TO INVOKE updateMsgStatus FUNCTION IN contactRepository class FOR CLOSING THE MSG

    public boolean updateMsgStatus(int contactId){
        boolean isUpdated = false;

        //Find the contact object in DB using findById method in CRUD repository
        Optional<Contact> contact = conRepo.findById(contactId); //-> returns the object if found otherwise returns null
        //Here Optional is used to handle the case where no object is found and findById returns null;

        //If contact found call the lambda function to update the status
        contact.ifPresent(contact1 -> {
            contact1.setStatus("CLOSED");
           // contact1.setUpdatedBy(updatedBy); --no need of this now as we are auditing these fields via auditor
           // contact1.setUpdatedAt(LocalDateTime.now()); --no need of this now as we are auditing these fields via auditor
        });

        //send back the contact obj after updating status to save it in DB
        Contact updatedContact = conRepo.save(contact.get()); // since we are passing the contact POJO object which is
        //already present in DB hence JPA will update the existing record in DB instead of creating a new row
        if(null!=updatedContact && updatedContact.getUpdatedBy()!=null){
            isUpdated = true;
        }
        return isUpdated;
    }
}
