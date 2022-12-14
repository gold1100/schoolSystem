package github.gold1100.schoolSystem.student;

import github.gold1100.schoolSystem.exceptions.EntityNotFoundException;
import github.gold1100.schoolSystem.mappers.StudentMapper;
import github.gold1100.schoolSystem.mappers.TeacherMapper;
import github.gold1100.schoolSystem.teacher.TeacherDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;
    private final TeacherMapper teacherMapper;

    public StudentService(StudentRepository studentRepository, StudentMapper studentMapper, TeacherMapper teacherMapper) {
        this.studentRepository = studentRepository;
        this.studentMapper = studentMapper;
        this.teacherMapper = teacherMapper;
    }

    public Page<StudentDTO> getAllStudents(Pageable pageable){
        return studentRepository
                .findAll(pageable)
                .map(studentMapper::toStudentDto);
    }
    public Page<StudentDTO> searchByName(String firstName, String lastName, Pageable pageable) {
        return studentRepository
                .findByName(firstName, lastName, pageable)
                .map(studentMapper::toStudentDto);
    }

    public StudentDTO createStudent(StudentDTO dto) {
        Student entity =  studentRepository.save(studentMapper.toStudentEntity(dto));
        return studentMapper.toStudentDto(entity);
    }

    public StudentDTO editStudentDetails(Long id, StudentDTO dto) {
        Student updatedStudent = fetchStudent(id);
        updateStudent(updatedStudent, dto);
        studentRepository.save(updatedStudent);
        return studentMapper.toStudentDto(updatedStudent);
    }

    public void deleteStudent(Long id) {
        studentRepository.delete(fetchStudent(id));
    }

    public Set<TeacherDTO> getTeacherList(Long id) {
        Student student = fetchStudent(id);
        return student
                .getTeachers()
                .stream()
                .map(teacherMapper::toTeacherDto)
                .collect(Collectors.toSet());
    }

    public Student fetchStudent(Long id){
        return studentRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(id));
    }

    private void updateStudent(Student updatedStudent, StudentDTO details) {
        updatedStudent.setFirstName(details.getFirstName());
        updatedStudent.setLastName(details.getLastName());
        updatedStudent.setAge(details.getAge());
        updatedStudent.setEmail(details.getEmail());
        updatedStudent.setCourse(details.getCourse());
    }


}
