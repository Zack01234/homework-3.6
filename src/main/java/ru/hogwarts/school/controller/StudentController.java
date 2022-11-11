package ru.hogwarts.school.controller;

import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.entity.Student;
import ru.hogwarts.school.record.FacultyRecord;
import ru.hogwarts.school.record.StudentRecord;
import ru.hogwarts.school.service.StudentService;

import java.util.Collection;

@RestController
@RequestMapping("/students")
public class StudentController {
    private final StudentService studentService;
    private Student studentRecord;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping
    public StudentRecord create(@RequestBody @Valid StudentRecord studentRecord) {
        return studentService.create(studentRecord);
    }

    @GetMapping("/{id}")
    public StudentRecord read(@PathVariable long id) {
        return studentService.read(id);
    }

    @PutMapping("/{id}")
    public StudentRecord update(@PathVariable long id, @RequestBody @Valid StudentRecord facultyRecord) {
        return studentService.update(id, studentRecord);
    }

    @DeleteMapping("/{id}")
    public StudentRecord delete(@PathVariable long id) {
        return studentService.delete(id);
    }

    @GetMapping
    public Collection<StudentRecord> findByAge(@RequestParam int age) {
        return studentService.findByAge(age);
    }

    @GetMapping
    public Collection<StudentRecord> findByAgeBetween(@RequestParam("min") int minAge, @RequestParam("max") int maxAge) {
        return studentService.findByAgeBetween(minAge, maxAge);
    }
    @GetMapping("/{id}/faculty")
    public FacultyRecord findFacultyByStudent(@PathVariable long id){
        return studentService.findFacultyByStudent(id);
    }

}
