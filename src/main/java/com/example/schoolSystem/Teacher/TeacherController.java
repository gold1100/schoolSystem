package com.example.schoolSystem.Teacher;


import com.example.schoolSystem.GenericPersonController;
import com.example.schoolSystem.Student.Student;
import com.example.schoolSystem.Student_Teacher.StudentTeacherService;
import com.example.schoolSystem.dto.MapperTeacher;
import com.example.schoolSystem.dto.TeacherDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Set;

@RestController
@RequestMapping(path = "school/api/v1/teachers")
public class TeacherController implements GenericPersonController<TeacherDTO> {

    private final TeacherService teacherService;
    private final StudentTeacherService studentTeacherService;
    private final MapperTeacher mapperTeacher;

    public TeacherController(TeacherService teacherService, StudentTeacherService studentTeacherService, MapperTeacher mapperTeacher) {
        this.teacherService = teacherService;
        this.studentTeacherService = studentTeacherService;
        this.mapperTeacher = mapperTeacher;
    }

    @GetMapping
    public Page<TeacherDTO> getAll(Pageable pageable){
        return teacherService.getAllTeachers(pageable).map(mapperTeacher::toDTO);
    }

    @GetMapping(path = "/search")
    public Page<TeacherDTO> searchByName(@RequestParam(required = false) String firstName,
                                         @RequestParam(required = false) String lastName,
                                         Pageable pageable){
        return teacherService.searchBy(firstName, lastName, pageable).map(mapperTeacher::toDTO);
    }

    @PostMapping
    public TeacherDTO create(@RequestBody @Valid TeacherDTO dto){
        Teacher entity = teacherService.createTeacher(mapperTeacher.toTeacher(dto));
        return mapperTeacher.toDTO(entity);
    }

    @PutMapping(path = "/{id}")
    public TeacherDTO editDetails(@PathVariable Long id,
                                  @RequestBody @Valid TeacherDTO dto){
        Teacher entity = teacherService.editTeacherDetails(id, mapperTeacher.toTeacher(dto));
        return mapperTeacher.toDTO(entity);
    }

    @DeleteMapping(path = "/{id}")
    public void delete(@PathVariable Long id){
        teacherService.deleteTeacher(id);
    }

    @GetMapping(path = "/{teacherId}/students")
    public Set<Student> getStudentListForTeacher(@PathVariable Long teacherId){
        return teacherService.getStudentList(teacherId);
    }

    @PutMapping(path = "/{teacherId}/students/{studentId}")
    public ResponseEntity<String> addStudentToTeacher(@PathVariable Long teacherId,
                                                      @PathVariable Long studentId){
        studentTeacherService.connectTeacherAndStudent(teacherId, studentId);
        return ResponseEntity.ok("sucess");
    }

    @DeleteMapping(path = "/{teacherId}/students/{studentId}")
    public void deleteStudentFromTeacher(@PathVariable Long teacherId,
                                         @PathVariable Long studentId){
        studentTeacherService.disconnectTeacherAndStudent(teacherId, studentId);
    }
}
