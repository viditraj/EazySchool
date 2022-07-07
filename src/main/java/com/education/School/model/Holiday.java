package com.education.School.model;

//Lombok is the dependency which is used to generate getters,setters,constructors and toString functions automatically
import lombok.Data;

@Data //@Data is used to implement Lombok
public class Holiday extends BaseEntity{
    private  String day;
    private  String reason;
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
