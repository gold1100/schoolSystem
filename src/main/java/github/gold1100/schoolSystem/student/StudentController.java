package github.gold1100.schoolSystem.student;


import github.gold1100.schoolSystem.GenericPersonController;
import github.gold1100.schoolSystem.teacher.TeacherDTO;
import github.gold1100.schoolSystem.teacherStudentRelationship.TeacherStudentRelationshipHandler;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Set;

@RestController
@RequestMapping(path = "school/api/v1/students")
public class StudentController implements GenericPersonController<StudentDTO> {

    private final StudentService studentService;
    private final TeacherStudentRelationshipHandler relationshipHandler;

    public StudentController(StudentService studentService, TeacherStudentRelationshipHandler relationshipHandler) {
        this.studentService = studentService;
        this.relationshipHandler = relationshipHandler;
    }

    @GetMapping
    public Page<StudentDTO> getAll(Pageable pageable){
        return studentService.getAllStudents(pageable);
    }

    @GetMapping(path = "/search")
    public Page<StudentDTO> searchByName(@RequestParam(required = false) String firstName,
                                         @RequestParam(required = false) String lastName,
                                         Pageable pageable) {
        return studentService.searchByName(firstName, lastName, pageable);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public StudentDTO create(@RequestBody @Valid StudentDTO dto){
        return studentService.createStudent(dto);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping(path = "/{id}")
    public StudentDTO editDetails(@PathVariable Long id,
                                  @RequestBody @Valid StudentDTO dto){
        return studentService.editStudentDetails(id, dto);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(path = "/{id}")
    public void delete(@PathVariable Long id){
        studentService.deleteStudent(id);
    }

    @GetMapping(path = "{id}/teachers")
    public Set<TeacherDTO> getTeacherListForStudent(@PathVariable Long id){
        return studentService.getTeacherList(id);

    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping(path = "/{studentId}/teachers/{teacherId}")
    public void addTeacherToStudent(@PathVariable Long studentId,
                                    @PathVariable Long teacherId){
        relationshipHandler.connectTeacherAndStudent(teacherId, studentId);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(path = "/{studentId}/teachers/{teacherId}")
    public void deleteTeacherFromStudent(@PathVariable Long studentId,
                                         @PathVariable Long teacherId){
        relationshipHandler.disconnectTeacherAndStudent(teacherId, studentId);
    }



}
