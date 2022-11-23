package com.example.schoolSystem.dto;

import com.example.schoolSystem.Teacher.Teacher;
import org.springframework.stereotype.Component;

@Component
public class MapperTeacher {


    public TeacherDTO toDTO(Teacher teacher){
        return new TeacherDTO(
                teacher.getId(),
                teacher.getFirstName(),
                teacher.getLastName(),
                teacher.getAge(),
                teacher.getEmail(),
                teacher.getSubjectTaught(),
                teacher.getStudents()
        );
    }

    public Teacher toTeacher(TeacherDTO teacherDTO){
        return new Teacher(
                teacherDTO.getFirstName(),
                teacherDTO.getLastName(),
                teacherDTO.getAge(),
                teacherDTO.getEmail(),
                teacherDTO.getSubjectTaught(),
                teacherDTO.getStudents()
        );
    }
}
