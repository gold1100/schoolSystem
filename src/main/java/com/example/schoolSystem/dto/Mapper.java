package com.example.schoolSystem.dto;

import com.example.schoolSystem.Student.Student;
import com.example.schoolSystem.Teacher.Teacher;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class Mapper {

    public TeacherDTO toTeacherDto(Teacher teacher){
        return new TeacherDTO(
                teacher.getId(),
                teacher.getFirstName(),
                teacher.getLastName(),
                teacher.getAge(),
                teacher.getEmail(),
                teacher.getSubjectTaught(),
                prepareStudentDtoSet(teacher.getStudents())
        );
    }

    public Teacher toTeacherEntity(TeacherDTO teacherDTO){
        return new Teacher(
                teacherDTO.getFirstName(),
                teacherDTO.getLastName(),
                teacherDTO.getAge(),
                teacherDTO.getEmail(),
                teacherDTO.getSubjectTaught()
        );
    }

    public StudentDTO toStudentDto(Student student){
        return new StudentDTO(
                student.getId(),
                student.getFirstName(),
                student.getLastName(),
                student.getAge(),
                student.getEmail(),
                student.getCourse(),
                prepareTeacherDtoSet(student.getTeachers())
        );
    }

    public Student toStudentEntity(StudentDTO studentDTO){
        return new Student(
                studentDTO.getFirstName(),
                studentDTO.getLastName(),
                studentDTO.getAge(),
                studentDTO.getEmail(),
                studentDTO.getCourse()
        );
    }

    private Set<TeacherDTO> prepareTeacherDtoSet(Set<Teacher> teachers){
        Set<TeacherDTO> dtoSet = new HashSet<>();
        for(Teacher t : teachers){
            TeacherDTO tDto = new TeacherDTO();
            tDto.setId(t.getId());
            tDto.setFirstName(t.getFirstName());
            tDto.setLastName(t.getLastName());
            tDto.setAge(t.getAge());
            tDto.setEmail(t.getEmail());
            tDto.setSubjectTaught(t.getSubjectTaught());
            tDto.setStudents(null);
            dtoSet.add(tDto);
        }
        return dtoSet;
    }

    private Set<StudentDTO> prepareStudentDtoSet(Set<Student> students){
        Set<StudentDTO> dtoSet = new HashSet<>();
        for(Student s : students){
            StudentDTO dto = new StudentDTO();
            dto.setId(s.getId());
            dto.setFirstName(s.getFirstName());
            dto.setLastName(s.getLastName());
            dto.setAge(s.getAge());
            dto.setEmail(s.getEmail());
            dto.setCourse(s.getCourse());
            dto.setTeachers(null);
            dtoSet.add(dto);
        }
        return dtoSet;
    }
}
