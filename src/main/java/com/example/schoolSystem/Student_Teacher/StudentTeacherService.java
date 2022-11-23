package com.example.schoolSystem.Student_Teacher;

import com.example.schoolSystem.Student.Student;
import com.example.schoolSystem.Student.StudentRepository;
import com.example.schoolSystem.Teacher.Teacher;
import com.example.schoolSystem.Teacher.TeacherRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class StudentTeacherService {

    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;

    public StudentTeacherService(StudentRepository studentRepository, TeacherRepository teacherRepository) {
        this.studentRepository = studentRepository;
        this.teacherRepository = teacherRepository;
    }

    public void connectTeacherAndStudent(Long teacherId, Long studentId){
        Student student = fetchStudentIfExists(studentId);
        Teacher teacher = fetchTeacherIfExist(teacherId);
        teacher.getStudents().add(student);
        teacherRepository.save(teacher);
    }

    public void disconnectTeacherAndStudent(Long teacherId, Long studentId) {
        Teacher teacher = fetchTeacherIfExist(teacherId);
        Student student = teacher.getStudents()
                .stream()
                .filter(stud -> stud.getId().equals(studentId))
                .findFirst()
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Student with id" + studentId + " is not associated with this teacher"));
        teacher.getStudents().remove(student);
        teacherRepository.save(teacher);
    }

    private Teacher fetchTeacherIfExist(Long id){
        return teacherRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Teacher with id" + id + " doesn't exist"));
    }

    private Student fetchStudentIfExists(Long id){
        return studentRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Student with id" + id + " doesn't exist"));
    }
}
