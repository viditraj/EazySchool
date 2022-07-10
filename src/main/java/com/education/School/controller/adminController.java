package com.education.School.controller;


import com.education.School.model.ClassRoom;
import com.education.School.model.Contact;
import com.education.School.model.Courses;
import com.education.School.model.Person;
import com.education.School.repository.classRoomRepository;
import com.education.School.repository.coursesRepository;
import com.education.School.repository.personRepository;
import com.education.School.service.ContactService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@Slf4j
@Data
@Controller
@RequestMapping("/admin")
public class adminController {

    @Autowired
    private ContactService contactService;

    @Autowired
    private classRoomRepository classRepo;

    @Autowired
    private personRepository personRepo;

    @Autowired
    private coursesRepository coursesRepo;

    //METHOD TO DISPLAY MESSAGES TO ADMIN
    @GetMapping("/displayMessages/page/{pageNum}")
    public ModelAndView displayMessages(Model model , @PathVariable(name ="pageNum") int pageNum,
                                        @RequestParam("sortField") String sortField, @RequestParam("sortDir") String sortDir){
        Page<Contact> msgPage = contactService.findMsgsWithOpenStatus(pageNum,sortField,sortDir);
        //INVOKING findMsgsWithOpenStatus function in contactService class to get msgs from db in form of List
        List<Contact> contactMsgs = msgPage.getContent();
        //injecting msgs into messages.html template and returning that template to display
        ModelAndView modelAndView = new ModelAndView("messages.html");
        model.addAttribute("currentPage" ,pageNum);
        model.addAttribute("totalPages",msgPage.getTotalPages());
        model.addAttribute("totalMsgs" , msgPage.getTotalElements());
        model.addAttribute("sortField" , sortField);
        model.addAttribute("sortDir",sortDir);
        model.addAttribute("reverseSortDir" , sortDir.equals("asc") ? "desc" : "asc");
        modelAndView.addObject("contactMsgs" , contactMsgs);
        return modelAndView;
    }

    //METHOD TO DISPLAY CLASSES TO ADMIN
    @RequestMapping("/displayClasses")
    public ModelAndView displayClasses(Model model){
        List<ClassRoom> classRooms = classRepo.findAll();
        ModelAndView modelAndView = new ModelAndView("classes.html");
        modelAndView.addObject("classRooms" , classRooms);
        modelAndView.addObject("classRoom", new ClassRoom());
        return modelAndView;
    }

    //METHOD TO CREATE NEW CLASS

    @RequestMapping("/addNewClass")
    public ModelAndView addNewClass(Model model , @ModelAttribute("classRoom") ClassRoom classRoom){
        classRepo.save(classRoom);
        ModelAndView modelAndView = new ModelAndView("redirect:/admin/displayClasses");
        return modelAndView;
    }

    @RequestMapping("/deleteClass")
    public ModelAndView deleteClass(Model model , @RequestParam int id){
        //Fetching class from DB using classId
        Optional<ClassRoom> classRoom = classRepo.findById(id);
        //Removing every Student enrolled in that class, and saving the person object before deleting the class
       for(Person person : classRoom.get().getPersons() ){
            person.setClassRoom(null);
            personRepo.save(person);
        }
        //Finally deleting the class from DB
        classRepo.deleteById(id);
        ModelAndView modelAndView = new ModelAndView("redirect:/admin/displayClasses");
        return modelAndView;
    }

    @RequestMapping("/displayStudents")
    public ModelAndView displayStudents(Model model , @RequestParam int classId , HttpSession session ,
                                        @RequestParam(value="error" ,required = false) String error){
        ModelAndView modelAndView = new ModelAndView("students.html");
        Optional<ClassRoom> classRoom = classRepo.findById(classId);
        modelAndView.addObject("classRoom" , classRoom.get()); //To fetch the actual object from Optional we have to use .get()
        modelAndView.addObject("person" , new Person()); // We are sending this new blank person Object
        // so that ADMIN can use this to add Students into the class using ADD button.

        //Saving the class so that when admin tries to add students in the class, we shall know in which class he wants to add student
        session.setAttribute("classRoom" , classRoom.get());

        //In case of no student found while adding student send error
        if(error!=null){
            String errorMessage = "Invalid Email address!";
            modelAndView.addObject("errorMessage" , errorMessage);
        }

        return modelAndView;
    }

    //METHOD TO ADD STUDENT VIA EMAIL
    @PostMapping("/addStudent")
    public ModelAndView addStudent(Model model ,@ModelAttribute("person") Person person ,HttpSession session){
        ModelAndView modelAndView = new ModelAndView();

        //fetch the class from session in which student is to be added
        ClassRoom classRoom = (ClassRoom) session.getAttribute("classRoom");

        //fetch the student from DB via his EMAIL
        Person personEntity = personRepo.readByEmail(person.getEmail());

        //If no person is found with given EMAIL send error
        if(personEntity == null || !(personEntity.getPersonId()>0)){
            //here we are adding error parameter and redirecting to displayStudent page. So when displayStudent page
            //gets this error PARAM it will show error saying invalid email found
            modelAndView.setViewName("redirect:/admin/displayStudents?classId="+classRoom.getClassId()+"&error=true");
            return modelAndView;
        }

        //If person found update the class_id attribute in that person's record
        personEntity.setClassRoom(classRoom);
        //Save back the person's details into DB
        Person per = personRepo.save(personEntity);
        //Add that person into class
        classRoom.getPersons().add(per);
        //Save the updated class details in DB
        classRepo.save(classRoom);
        //return the view successfully
        modelAndView.setViewName("redirect:/admin/displayStudents?classId="+classRoom.getClassId());
        return modelAndView;
    }

    //METHOD TO DELETE STUDENT FROM CLASS USING EMAIL
    @GetMapping("/deleteStudent")
    public ModelAndView deleteStudent(Model model, HttpSession session , @RequestParam int personId){
        //fetching class from session
        ClassRoom classRoom = (ClassRoom) session.getAttribute("classRoom");
        //fetching person using Email
        Optional<Person> person = personRepo.findById(personId);
        //removing class id from person object
        person.get().setClassRoom(null);
        //Saving person object
        personRepo.save(person.get());
        //removing this student from classRoom's students list
        classRoom.getPersons().remove(person.get().getPersonId());
        //saving the updated class into DB
        ClassRoom savedClass = classRepo.save(classRoom);
        session.setAttribute("classRoom" , savedClass);
        ModelAndView modelAndView = new ModelAndView("redirect:/admin/displayStudents?classId="+classRoom.getClassId());

        return modelAndView;
    }

    //METHOD TO DISPLAY COURSES TO ADMIN
    @RequestMapping("displayCourses")
    public ModelAndView displayCourses(Model model){
        List<Courses> courses = coursesRepo.findAll();
        ModelAndView modelAndView = new ModelAndView("courses_secure.html");
        modelAndView.addObject("courses" , courses);
        modelAndView.addObject("course" , new Courses());
        return modelAndView;
    }

    //METHOD TO ADD NEW COURSE
    @PostMapping("addNewCourse")
    public ModelAndView addNewCourse(Model model, @ModelAttribute("course") Courses course){
        coursesRepo.save(course);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/admin/displayCourses");
        return modelAndView;
    }

    //METHOD TO VIEW STUDENTS IN A COURSE
    @RequestMapping("/viewStudents")
    public ModelAndView viewStudents(Model model , @RequestParam int id ,
            @RequestParam(value ="error" , required = false) String error, HttpSession session){
        ModelAndView modelAndView = new ModelAndView("course_students.html");
        Optional<Courses> courses = coursesRepo.findById(id);
        session.setAttribute("courses",courses.get());
        modelAndView.addObject("courses",courses.get());
        modelAndView.addObject("person" , new Person());

        if(error!=null){
            String errorMessage = "No student exists with that email!";
            modelAndView.addObject("errorMessage", errorMessage);
        }
        return modelAndView;
    }

    //METHOD TO ADD STUDENTS TO A COURSE
    @PostMapping("/addStudentToCourse")
    public ModelAndView addStudentToCourse(Model model , @ModelAttribute("person") Person person,
                                           HttpSession session){
        ModelAndView modelAndView = new ModelAndView();
    Courses courses = (Courses) session.getAttribute("courses");
    Person personEntity = personRepo.readByEmail(person.getEmail());
    if(personEntity==null || !(personEntity.getPersonId()>0)){
        modelAndView.setViewName("redirect:/admin/viewStudents?id="+courses.getCourseId()
                                 +"&error=true");
        return modelAndView;
    }
    personEntity.getCourses().add(courses);
    courses.getPersons().add(personEntity);
    personRepo.save(personEntity);
    session.setAttribute("courses",courses);
    modelAndView.setViewName("redirect:/admin/viewStudents?id="+courses.getCourseId());
    return  modelAndView;
    }


    //METHOD TO REMOVE STUDENT FROM A COURSE
    @RequestMapping("/deleteStudentFromCourse")
    public ModelAndView deleteStudentFromCourse(Model model, @RequestParam int personId , HttpSession session){
        Courses courses = (Courses) session.getAttribute("courses");
        Optional<Person> personEntity = personRepo.findById(personId);
        personEntity.get().getCourses().remove(courses);
        courses.getPersons().remove(personEntity);
        personRepo.save(personEntity.get());
        session.setAttribute("courses",courses);
        ModelAndView modelAndView = new ModelAndView("redirect:/admin/viewStudents?id="+courses.getCourseId());
        return modelAndView;
    }
}

