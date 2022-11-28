package com.example.schoolSystem.Teacher;


import com.example.schoolSystem.Student.Student;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Table
@Entity
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;
    private String firstName;
    private String lastName;
    private int age;
    private String email;
    private String subjectTaught;
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "Student_Teacher",
            joinColumns = @JoinColumn(name = "teacher_id"),
            inverseJoinColumns = @JoinColumn(name = "student_id")
    )
    @Fetch(FetchMode.SUBSELECT)
    private Set<Student> students = new HashSet<>();

    public Teacher() {
    }

    public Teacher(String firstName, String lastName, int age, String email, String subjectTaught) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.email = email;
        this.subjectTaught = subjectTaught;
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

    public void setStudents(Set<Student> students) {
        this.students = students;
    }

    public Set<Student> getStudents() {
        return students;
    }

    public void addStudent(Student student){
        this.students.add(student);
        student.getTeachers().add(this);
    }

    public void removeStudent(long studentId){
        Student student = this.students.stream().filter(s -> s.getId() == studentId).findFirst().orElse(null);
        if(student != null){
            this.students.remove(student);
            student.getTeachers().remove(this);
        }
    }
}
