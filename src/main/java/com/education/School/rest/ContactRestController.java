package com.education.School.rest;


import com.education.School.model.Contact;
import com.education.School.model.Person;
import com.education.School.model.Response;
import com.education.School.repository.contactRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RestController  // Combination of @ResponseBody + @Controller
@RequestMapping("/api/contact")
@CrossOrigin(origins = "*") // To enable different UI apps running on different servers to access this REST API
public class ContactRestController {

    /*
    In REST APIs we do not maintain any VIEW related information. Here we simply return the
    desired result back to the user who is using our REST Api in the JSON or XML format.
    SPRING Framework uses JACKSON library to convert JAVA POJO into JSON format
     */
    @Autowired
    private contactRepository conRepo;


    @GetMapping("/getMessagesByStatus")
    //@ResponseBody  --> No need now as we are using @RestController
    public List<Contact> getMessagesByStatus(@RequestParam(name="status") String status){
        return conRepo.findByStatus(status); //Note: no view related info, simply returning the required query result.
    }

    @GetMapping("/getMessagesByPerson")
    //@ResponseBody  --> No need now as we are using @RestController
    public List<Contact> getMessagesByPerson(@RequestBody Person person){
        if(person!=null && person.getEmail()!=null){
            return conRepo.findByEmail(person.getEmail());
        }
        else
        return List.of();
    }

    //REST METHOD TO CREATE/SAVE CONTACT VIA API
    @PostMapping("/saveMsg")
    public ResponseEntity<Response> saveMsg(@RequestHeader("invocationFrom") String invocationFrom,
                                           @Valid @RequestBody Contact contact){
        log.info(String.format("Header Invocation from = %s",invocationFrom));
        conRepo.save(contact);
        //Populating Response
        Response response = new Response();
        response.setStatusCode("200");
        response.setStatusMsg("Contact saved successfully!!!");
        //Sending Response with status header and body content
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .header("isSavedMsg","true")
                .body(response);
    }

    //REST METHOD TO DELETE CONTACT VIA API

    @DeleteMapping("/deleteMsg")
    public ResponseEntity<Response> deleteMsg(RequestEntity<Contact> requestEntity){ //--2nd method to accept all headers and body

        //Getting Headers from the requestEntity , it can be used to implement diff business logic based on diff header values
       /* HttpHeaders headers = requestEntity.getHeaders();
        headers.forEach((key,value)->{
            log.info(String.format(
                    "Header '%s' = %s ",key,value.stream().collect(Collectors.joining("|"))));
        }); */
        Contact contact = requestEntity.getBody();
        conRepo.deleteById(contact.getContactId());
        Response response = new Response();
        response.setStatusCode("200");
        response.setStatusMsg("Contact deleted successfully!!!");
        //Sending Response with status header and body content
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .header("isDeletedMsg","true")
                .body(response);
    }


    //PATCH METHOD TO UPDATE THE STATUS OF THE MESSAGE
    @PatchMapping("/closeMsg")
    public ResponseEntity<Response> closeMsg(RequestEntity<Contact> contactReq){

        Response response = new Response();
        Contact contactBody = contactReq.getBody();
        Optional<Contact> contact = conRepo.findById(contactBody.getContactId());
        if(contact.isPresent()){
            contact.get().setStatus("CLOSED");
            conRepo.save(contact.get());
        }
        else{
            response.setStatusCode("400");
            response.setStatusMsg("contact not found!");
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(response);
        }
        response.setStatusCode("200");
        response.setStatusMsg("contact closed successfully");
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(response);
    }

}
