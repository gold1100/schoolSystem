package com.example.schoolSystem.Student;


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
@RequestMapping(path = "school/api/v1/students")
public class StudentController implements GenericPersonController<StudentDTO> {

    private final StudentService studentService;
    private final Mapper mapper;

    public StudentController(StudentService studentService, Mapper mapper) {
        this.studentService = studentService;
        this.mapper = mapper;
    }

    @GetMapping
    public Page<StudentDTO> getAll(Pageable pageable){
        return studentService.getAllStudents(pageable).map(mapper::toStudentDto);
    }

    @GetMapping(path = "/search")
    public Page<StudentDTO> searchByName(@RequestParam(required = false) String firstName,
                                         @RequestParam(required = false) String lastName,
                                         Pageable pageable) {
        return studentService.searchByName(firstName, lastName, pageable).map(mapper::toStudentDto);
    }

    @PostMapping
    public StudentDTO create(@RequestBody @Valid StudentDTO dto){
        Student student = studentService.createStudent(mapper.toStudentEntity(dto));
        return mapper.toStudentDto(student);
    }

    @PutMapping(path = "/{id}")
    public StudentDTO editDetails(@PathVariable Long id,
                                  @RequestBody @Valid StudentDTO dto){
        Student student = studentService.editStudentDetails(id, mapper.toStudentEntity(dto));
        return mapper.toStudentDto(student);
    }

    @DeleteMapping(path = "/{id}")
    public void delete(@PathVariable Long id){
        studentService.deleteStudent(id);
    }

    @GetMapping(path = "{id}/teachers")
    public Set<TeacherDTO> getTeacherListForStudent(@PathVariable Long id){
        return studentService.getTeacherList(id).stream().map(mapper::toTeacherDto).collect(Collectors.toSet());

    }

    @PutMapping(path = "/{studentId}/teachers/{teacherId}")
    public ResponseEntity<Object> addTeacherToStudent(@PathVariable Long studentId,
                                                      @PathVariable Long teacherId){
        studentService.connectTeacherAndStudent(teacherId, studentId);
        return ResponseEntity.ok("Successfully connected student and teacher");
    }

    @DeleteMapping(path = "/{studentId}/teachers/{teacherId}")
    public void deleteTeacherFromStudent(@PathVariable Long studentId,
                                         @PathVariable Long teacherId){
        studentService.disconnectTeacherAndStudent(teacherId, studentId);
    }



}
