package com.example.schoolSystem.dto;

import com.example.schoolSystem.Teacher.Teacher;

import javax.validation.constraints.*;
import java.util.Set;

public class StudentDTO {
    private Long id;
    @Size(min = 2)
    @NotNull
    private String firstName;
    @Size(min = 2)
    @NotNull
    private String lastName;
    @Min(18)
    @Max(130)
    private int age;
    @Email
    private String email;
    private String course;
    private Set<Teacher> teachers;

    public StudentDTO() {
    }

    public StudentDTO(Long id, String firstName, String lastName, int age, String email, String course, Set<Teacher> teachers) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.email = email;
        this.course = course;
        this.teachers = teachers;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public Set<Teacher> getTeachers() {
        return teachers;
    }

    public void setTeachers(Set<Teacher> teachers) {
        this.teachers = teachers;
    }
}
