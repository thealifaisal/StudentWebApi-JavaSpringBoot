package org.scalable.student_registration_web_api.student;

import lombok.AllArgsConstructor;
import org.scalable.student_registration_web_api.student.dtos.StudentDto;
import org.scalable.student_registration_web_api.student.exceptions.StudentAlreadyExistsException;
import org.scalable.student_registration_web_api.student.exceptions.StudentNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("api/v1/students")
public class StudentController {

    private final StudentService studentService;

    @GetMapping
    public List<StudentDto> getAllStudents() {
        return studentService.getAllStudents();
    }

    @PostMapping
    public void addStudent(@RequestBody StudentDto studentDto) throws StudentAlreadyExistsException {
        studentService.addStudent(studentDto);
    }

    @DeleteMapping("/{id}")
    public void deleteStudent(@PathVariable Long id) throws StudentNotFoundException {
        studentService.deleteStudent(id);
    }
}
