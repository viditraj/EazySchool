package com.education.School.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

//Not adding @Data beacuse @Data converts each object to .toString
// which sometimes give errors hence we are using only @Getter and @Setter
//in place of @Data
@Getter
@Setter
@Entity
public class Courses extends BaseEntity{


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO,generator = "native")
    @GenericGenerator(name="native" , strategy = "native")
    private int courseId;

    private String name;

    private String fees;

    @ManyToMany(mappedBy = "courses" , fetch = FetchType.EAGER ,cascade = CascadeType.PERSIST)
    private Set<Person> persons = new HashSet<>();
}
