package github.gold1100.schoolSystem.teacherStudentRelationship;

import github.gold1100.schoolSystem.student.Student;
import github.gold1100.schoolSystem.student.StudentService;
import github.gold1100.schoolSystem.teacher.Teacher;
import github.gold1100.schoolSystem.teacher.TeacherService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Component
public class TeacherStudentRelationshipHandler {

    private final TeacherService teacherService;
    private final StudentService studentService;

    public TeacherStudentRelationshipHandler(TeacherService teacherService, StudentService studentService) {
        this.teacherService = teacherService;
        this.studentService = studentService;
    }

    public void connectTeacherAndStudent(Long teacherId, Long studentId){
        Student student = studentService.fetchStudent(studentId);
        Teacher teacher = teacherService.fetchTeacher(teacherId);
        teacher.addStudent(student);
    }

    public void disconnectTeacherAndStudent(Long teacherId, Long studentId) {
        Teacher teacher = teacherService.fetchTeacher(teacherId);
        teacher.removeStudent(studentId);
    }

}
