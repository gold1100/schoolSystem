package com.example.schoolSystem.Teacher;


import com.example.schoolSystem.GenericPersonController;
import com.example.schoolSystem.dto.Mapper;
import com.example.schoolSystem.dto.StudentDTO;
import com.example.schoolSystem.dto.TeacherDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "school/api/v1/teachers")
public class TeacherController implements GenericPersonController<TeacherDTO> {

    private final TeacherService teacherService;
    private final Mapper mapper;

    public TeacherController(TeacherService teacherService, Mapper mapper) {
        this.teacherService = teacherService;
        this.mapper = mapper;
    }

    @GetMapping
    public Page<TeacherDTO> getAll(Pageable pageable){
        return teacherService.getAllTeachers(pageable).map(mapper::toTeacherDto);
    }

    @GetMapping(path = "/search")
    public Page<TeacherDTO> searchByName(@RequestParam(required = false) String firstName,
                                         @RequestParam(required = false) String lastName,
                                         Pageable pageable){
        return teacherService.searchBy(firstName, lastName, pageable).map(mapper::toTeacherDto);
    }

    @PostMapping
    public TeacherDTO create(@RequestBody @Valid TeacherDTO dto){
        Teacher entity = teacherService.createTeacher(mapper.toTeacherEntity(dto));
        return mapper.toTeacherDto(entity);
    }

    @PutMapping(path = "/{id}")
    public TeacherDTO editDetails(@PathVariable Long id,
                                  @RequestBody @Valid TeacherDTO dto){
        Teacher entity = teacherService.editTeacherDetails(id, mapper.toTeacherEntity(dto));
        return mapper.toTeacherDto(entity);
    }

    @DeleteMapping(path = "/{id}")
    public void delete(@PathVariable Long id){
        teacherService.deleteTeacher(id);
    }

    @GetMapping(path = "/{teacherId}/students")
    public Set<StudentDTO> getStudentListForTeacher(@PathVariable Long teacherId){
        return teacherService.getStudentList(teacherId).stream().map(mapper::toStudentDto).collect(Collectors.toSet());
    }

    @PutMapping(path = "/{teacherId}/students/{studentId}")
    public ResponseEntity<String> addStudentToTeacher(@PathVariable Long teacherId,
                                                      @PathVariable Long studentId){
        teacherService.connectTeacherAndStudent(teacherId, studentId);
        return ResponseEntity.ok("Successfully connected student and teacher");
    }

    @DeleteMapping(path = "/{teacherId}/students/{studentId}")
    public void deleteStudentFromTeacher(@PathVariable Long teacherId,
                                         @PathVariable Long studentId){
        teacherService.disconnectTeacherAndStudent(teacherId, studentId);
    }
}
