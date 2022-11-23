package com.example.schoolSystem.Student;


import com.example.schoolSystem.GenericPersonController;
import com.example.schoolSystem.Student_Teacher.StudentTeacherService;
import com.example.schoolSystem.Teacher.Teacher;
import com.example.schoolSystem.dto.MapperStudent;
import com.example.schoolSystem.dto.StudentDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Set;

@RestController
@RequestMapping(path = "school/api/v1/students")
public class StudentController implements GenericPersonController<StudentDTO> {

    private final StudentService studentService;
    private final MapperStudent mapperStudent;
    private final StudentTeacherService studentTeacherService;

    public StudentController(StudentService studentService, MapperStudent mapperStudent, StudentTeacherService studentTeacherService) {
        this.studentService = studentService;
        this.mapperStudent = mapperStudent;
        this.studentTeacherService = studentTeacherService;
    }

    @GetMapping
    public Page<StudentDTO> getAll(Pageable pageable){
        return studentService.getAllStudents(pageable).map(mapperStudent::toDTO);
    }

    @GetMapping(path = "/search")
    public Page<StudentDTO> searchByName(@RequestParam(required = false) String firstName,
                                         @RequestParam(required = false) String lastName,
                                         Pageable pageable) {
        return studentService.searchByName(firstName, lastName, pageable).map(mapperStudent::toDTO);
    }

    @PostMapping
    public StudentDTO create(@RequestBody @Valid StudentDTO dto){
        Student student = studentService.createStudent(mapperStudent.toStudent(dto));
        return mapperStudent.toDTO(student);
    }

    @PutMapping(path = "/{id}")
    public StudentDTO editDetails(@PathVariable Long id,
                                  @RequestBody @Valid StudentDTO dto){
        Student student = studentService.editStudentDetails(id, mapperStudent.toStudent(dto));
        return mapperStudent.toDTO(student);
    }

    @DeleteMapping(path = "/{id}")
    public void delete(@PathVariable Long id){
        studentService.deleteStudent(id);
    }

    @GetMapping(path = "{id}/teachers")
    public Set<Teacher> getTeacherListForStudent(@PathVariable Long id){
        return studentService.getTeacherList(id);

    }

    @PutMapping(path = "/{studentId}/teachers/{teacherId}")
    public ResponseEntity<String> addTeacherToStudent(@PathVariable Long studentId,
                                                      @PathVariable Long teacherId){
        studentTeacherService.connectTeacherAndStudent(teacherId, studentId);
        return ResponseEntity.ok("Success");
    }

    @DeleteMapping(path = "/{studentId}/teachers/{teacherId}")
    public void deleteTeacherFromStudent(@PathVariable Long studentId,
                                         @PathVariable Long teacherId){
        studentTeacherService.disconnectTeacherAndStudent(teacherId, studentId);
    }



}
