package org.scalable.amigoscodetests.student;

import lombok.AllArgsConstructor;
import org.scalable.amigoscodetests.student.exceptions.StudentAlreadyExistsException;
import org.scalable.amigoscodetests.student.exceptions.StudentNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("api/v1/students")
public class StudentController {

    private final StudentService studentService;

    @GetMapping
    public List<Student> getAllStudents() {
        return studentService.getAllStudents();
    }

    @PostMapping
    public void addStudent(@RequestBody Student student) throws StudentAlreadyExistsException {
        studentService.addStudent(student);
    }

    @DeleteMapping("/{id}")
    public void deleteStudent(@PathVariable Long id) throws StudentNotFoundException {
        studentService.deleteStudent(id);
    }
}
