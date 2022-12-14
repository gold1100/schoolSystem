package github.gold1100.schoolSystem.mappers;

import github.gold1100.schoolSystem.student.Student;
import github.gold1100.schoolSystem.student.StudentDTO;
import org.springframework.stereotype.Component;

@Component
public class StudentMapper {
    public StudentDTO toStudentDto(Student student){
        return new StudentDTO(
                student.getId(),
                student.getFirstName(),
                student.getLastName(),
                student.getAge(),
                student.getEmail(),
                student.getCourse()
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
}
