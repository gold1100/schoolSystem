package com.example.schoolSystem.Teacher;

import com.example.schoolSystem.Student.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Set;

@Service
public class TeacherService {

    private final TeacherRepository teacherRepository;

    public TeacherService(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }


    public Page<Teacher> getAllTeachers(Pageable pageable) {
        return teacherRepository.findAll(pageable);
    }

    public Page<Teacher> searchBy(String firstName, String lastName, Pageable pageable) {
        if(firstName != null){
            firstName = firstName.toLowerCase();
        }
        if(lastName != null){
            lastName = lastName.toLowerCase();
        }
        return teacherRepository.findByName(firstName, lastName, pageable);
    }

    public Teacher createTeacher(Teacher teacher){
        return teacherRepository.save(teacher);
    }

    public Teacher editTeacherDetails(Long id, Teacher details) {
        Teacher updatedTeacher = fetchTeacherIfExist(id);
        updateTeacherObj(updatedTeacher, details);
        return teacherRepository.save(updatedTeacher);
    }

    public void deleteTeacher(Long id) {
       Teacher toDelete = fetchTeacherIfExist(id);
       teacherRepository.delete(toDelete);
    }

    public Set<Student> getStudentList(Long teacherId) {
        Teacher teacher = fetchTeacherIfExist(teacherId);
        return teacher.getStudents();
    }

    // private methods
    private Teacher fetchTeacherIfExist(Long id){
        return teacherRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Teacher with id" + id + " doesn't exist"));
    }

    private void updateTeacherObj(Teacher updatedTeacher, Teacher details){
        updatedTeacher.setFirstName(details.getFirstName());
        updatedTeacher.setLastName(details.getLastName());
        updatedTeacher.setAge(details.getAge());
        updatedTeacher.setSubjectTaught(details.getSubjectTaught());
    }



}