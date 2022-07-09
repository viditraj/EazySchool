package com.education.School.model;

//Lombok is the dependency which is used to generate getters,setters,constructors and toString functions automatically
import lombok.Data;

import javax.persistence.*;

@Data //@Data is used to implement Lombok
@Entity
@Table(name = "holidays")
public class Holiday extends BaseEntity{

    @Id
    private  String day;
    private  String reason;

    @Enumerated(EnumType.STRING) // To convert enum Type to String during Storing holiday type in DB
    private  Type type;

    public enum Type{
        FESTIVAL,
        FEDERAL
    }

//HERE GETTER AND SETTER AND CONSTRUCTORS AND TOSTRING ARE COMMENTED FOR REFERENCE AS THEY WILL AUTOMATICALLY
//GENERATED AT RUNTIME BY LOMBOK DEPENDENCY

   /* public Holiday(String day, String reason, Type type) {
        this.day = day;
        this.reason = reason;
        this.type = type;
    }
    public String getDay() {
        return day;
    }

    public String getReason() {
        return reason;
    }

    public Type getType() {
        return type;
    }

    @Override
    public String toString() {
        return "Holiday{" +
                "day='" + day + '\'' +
                ", reason='" + reason + '\'' +
                ", type=" + type +
                '}';
    } */
}
