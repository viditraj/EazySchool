package com.education.School.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;
@Setter
@Getter
@Entity
@Table(name = "class")
public class ClassRoom extends BaseEntity{


    @OneToMany(mappedBy = "classRoom" , fetch = FetchType.LAZY,cascade = CascadeType.PERSIST,targetEntity = Person.class)
     private Set<Person> persons;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO,generator = "native")
    @GenericGenerator(name="native" , strategy = "native")
    private int classId;

    @NotBlank(message = "class name can't be blank")
    @Size(min=4 , message = "Class name must be at least 4 characters long")
    private String name;

}
