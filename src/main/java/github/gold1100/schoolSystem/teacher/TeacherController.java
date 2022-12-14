package github.gold1100.schoolSystem.teacher;


import github.gold1100.schoolSystem.GenericPersonController;
import github.gold1100.schoolSystem.student.StudentDTO;
import github.gold1100.schoolSystem.teacherStudentRelationship.TeacherStudentRelationshipHandler;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Set;

@RestController
@RequestMapping(path = "school/api/v1/teachers")
public class TeacherController implements GenericPersonController<TeacherDTO> {

    private final TeacherService teacherService;
    private final TeacherStudentRelationshipHandler relationshipHandler;

    public TeacherController(TeacherService teacherService, TeacherStudentRelationshipHandler relationshipHandler) {
        this.teacherService = teacherService;
        this.relationshipHandler = relationshipHandler;
    }

    @GetMapping
    public Page<TeacherDTO> getAll(Pageable pageable){
        return teacherService.getAllTeachers(pageable);
    }

    @GetMapping(path = "/search")
    public Page<TeacherDTO> searchByName(@RequestParam(required = false) String firstName,
                                         @RequestParam(required = false) String lastName,
                                         Pageable pageable){
        return teacherService.searchBy(firstName, lastName, pageable);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TeacherDTO create(@RequestBody @Valid TeacherDTO dto){
        return teacherService.createTeacher(dto);
    }

    @PutMapping(path = "/{id}")
    public TeacherDTO editDetails(@PathVariable Long id,
                                  @RequestBody @Valid TeacherDTO dto){
        return teacherService.editTeacherDetails(id, dto);
    }

    @DeleteMapping(path = "/{id}")
    public void delete(@PathVariable Long id){
        teacherService.deleteTeacher(id);
    }

    @GetMapping(path = "/{teacherId}/students")
    public Set<StudentDTO> getStudentListForTeacher(@PathVariable Long teacherId){
        return teacherService.getStudentList(teacherId);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping(path = "/{teacherId}/students/{studentId}")
    public void addStudentToTeacher(@PathVariable Long teacherId,
                                                      @PathVariable Long studentId){
        relationshipHandler.connectTeacherAndStudent(teacherId, studentId);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(path = "/{teacherId}/students/{studentId}")
    public void deleteStudentFromTeacher(@PathVariable Long teacherId,
                                         @PathVariable Long studentId){
        relationshipHandler.disconnectTeacherAndStudent(teacherId, studentId);
    }
}
