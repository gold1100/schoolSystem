package com.example.schoolSystem.Student;

import com.example.schoolSystem.Teacher.Teacher;
import com.example.schoolSystem.Teacher.TeacherService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Set;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final TeacherService teacherService;

    public StudentService(StudentRepository studentRepository, TeacherService teacherService) {
        this.studentRepository = studentRepository;
        this.teacherService = teacherService;
    }

    public Page<Student> getAllStudents(Pageable pageable){
        return studentRepository.findAll(pageable);
    }
    public Page<Student> searchByName(String firstName, String lastName, Pageable pageable) {
        return studentRepository.findByName(firstName, lastName, pageable);
    }

    public Student createStudent(Student student) {
        return studentRepository.save(student);
    }

    public Student editStudentDetails(Long id, Student details) {
        Student updatedStudent = fetchStudentIfExists(id);
        updateStudent(updatedStudent, details);
        studentRepository.save(updatedStudent);
        return updatedStudent;
    }

    public void deleteStudent(Long id) {
        studentRepository.delete(fetchStudentIfExists(id));
    }

    public Set<Teacher> getTeacherList(Long id) {
        Student student = fetchStudentIfExists(id);
        return student.getTeachers();
    }

    public void connectTeacherAndStudent(Long teacherId, Long studentId){
        teacherService.connectTeacherAndStudent(teacherId, studentId);
    }

    public void disconnectTeacherAndStudent(Long teacherId, Long studentId) {
        teacherService.disconnectTeacherAndStudent(teacherId, studentId);
    }

    // private methods
    private Student fetchStudentIfExists(Long id){
        return studentRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Student with id" + id + " doesn't exist"));
    }

    private void updateStudent(Student updatedStudent, Student details) {
        updatedStudent.setFirstName(details.getFirstName());
        updatedStudent.setLastName(details.getLastName());
        updatedStudent.setAge(details.getAge());
        updatedStudent.setEmail(details.getEmail());
        updatedStudent.setCourse(details.getCourse());
    }


}
