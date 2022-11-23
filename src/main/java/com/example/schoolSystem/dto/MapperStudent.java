package com.example.schoolSystem.dto;

import com.example.schoolSystem.Student.Student;
import org.springframework.stereotype.Component;

@Component
public class MapperStudent {

    public StudentDTO toDTO(Student student){
        return new StudentDTO(
                student.getId(),
                student.getFirstName(),
                student.getLastName(),
                student.getAge(),
                student.getEmail(),
                student.getCourse(),
                student.getTeachers()
        );
    }

    public Student toStudent(StudentDTO studentDTO){
        return new Student(
                studentDTO.getFirstName(),
                studentDTO.getLastName(),
                studentDTO.getAge(),
                studentDTO.getEmail(),
                studentDTO.getCourse(),
                studentDTO.getTeachers()
        );
    }
}
