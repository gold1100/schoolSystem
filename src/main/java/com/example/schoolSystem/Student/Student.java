package com.example.schoolSystem.Student;

import com.example.schoolSystem.Teacher.Teacher;
import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.Set;

@Table
@Entity
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;
    private String firstName;
    private String lastName;
    private int age;
    private String email;
    private String course;
    @ManyToMany(mappedBy = "students", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JsonBackReference
    @Fetch(FetchMode.SUBSELECT)
    private Set<Teacher> teachers;

    public Student() {
    }

    public Student(String firstName, String lastName, int age, String email, String course, Set<Teacher> teachers) {
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
