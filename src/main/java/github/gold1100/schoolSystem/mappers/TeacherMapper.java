package github.gold1100.schoolSystem.mappers;

import github.gold1100.schoolSystem.teacher.Teacher;
import github.gold1100.schoolSystem.teacher.TeacherDTO;
import org.springframework.stereotype.Component;

@Component
public class TeacherMapper {
    public TeacherDTO toTeacherDto(Teacher teacher){
        return new TeacherDTO(
                teacher.getId(),
                teacher.getFirstName(),
                teacher.getLastName(),
                teacher.getAge(),
                teacher.getEmail(),
                teacher.getSubjectTaught()
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
}
