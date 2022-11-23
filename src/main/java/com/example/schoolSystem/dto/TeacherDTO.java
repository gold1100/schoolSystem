package com.example.schoolSystem.dto;

import com.example.schoolSystem.Student.Student;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.*;
import java.util.Set;

public class TeacherDTO {
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
    private String subjectTaught;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JsonManagedReference
    private Set<Student> students;


    public TeacherDTO() {
    }

    public TeacherDTO(Long id, String firstName, String lastName, int age, String email, String subjectTaught, Set<Student> students) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.email = email;
        this.subjectTaught = subjectTaught;
        this.students = students;
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

    public String getSubjectTaught() {
        return subjectTaught;
    }

    public void setSubjectTaught(String subjectTaught) {
        this.subjectTaught = subjectTaught;
    }

    public Set<Student> getStudents() {
        return students;
    }

    public void setStudents(Set<Student> students) {
        this.students = students;
    }
}
