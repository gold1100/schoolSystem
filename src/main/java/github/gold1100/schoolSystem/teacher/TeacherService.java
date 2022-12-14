package github.gold1100.schoolSystem.teacher;

import github.gold1100.schoolSystem.exceptions.EntityNotFoundException;
import github.gold1100.schoolSystem.mappers.StudentMapper;
import github.gold1100.schoolSystem.mappers.TeacherMapper;
import github.gold1100.schoolSystem.student.StudentDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TeacherService {

    private final TeacherRepository teacherRepository;
    private final TeacherMapper teacherMapper;
    private final StudentMapper studentMapper;

    public TeacherService(TeacherRepository teacherRepository, TeacherMapper teacherMapper, StudentMapper studentMapper) {
        this.teacherRepository = teacherRepository;
        this.teacherMapper = teacherMapper;
        this.studentMapper = studentMapper;
    }


    public Page<TeacherDTO> getAllTeachers(Pageable pageable) {
        return teacherRepository
                .findAll(pageable)
                .map(teacherMapper::toTeacherDto);
    }

    public Page<TeacherDTO> searchBy(String firstName, String lastName, Pageable pageable) {
        return teacherRepository
                .findByName(firstName, lastName, pageable)
                .map(teacherMapper::toTeacherDto);
    }

    public TeacherDTO createTeacher(TeacherDTO dto){
        Teacher entity = teacherRepository.save(teacherMapper.toTeacherEntity(dto));
        return teacherMapper.toTeacherDto(entity);
    }

    public TeacherDTO editTeacherDetails(Long id, TeacherDTO details) {
        Teacher updatedTeacher = fetchTeacher(id);
        updateTeacherEntity(updatedTeacher, details);
        teacherRepository.save(updatedTeacher);
        return teacherMapper.toTeacherDto(updatedTeacher);
    }

    public void deleteTeacher(Long id) {
       Teacher teacher = fetchTeacher(id);
       teacherRepository.delete(teacher);
    }

    public Set<StudentDTO> getStudentList(Long teacherId) {
        Teacher teacher = fetchTeacher(teacherId);
        return teacher
                .getStudents()
                .stream()
                .map(studentMapper::toStudentDto)
                .collect(Collectors.toSet());
    }

    public Teacher fetchTeacher(Long id){
        return teacherRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException(id));
    }

    private void updateTeacherEntity(Teacher updatedTeacher, TeacherDTO details){
        updatedTeacher.setFirstName(details.getFirstName());
        updatedTeacher.setLastName(details.getLastName());
        updatedTeacher.setAge(details.getAge());
        updatedTeacher.setSubjectTaught(details.getSubjectTaught());
    }



}