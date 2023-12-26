package org.scalable.student_registration_web_api.student;

import lombok.AllArgsConstructor;
import org.scalable.student_registration_web_api.student.exceptions.StudentAlreadyExistsException;
import org.scalable.student_registration_web_api.student.exceptions.StudentNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public void addStudent(Student student) throws StudentAlreadyExistsException {
        var studentExists = studentRepository.selectExistsEmail(student.getEmail());
        if (studentExists)
            throw new StudentAlreadyExistsException();
        studentRepository.save(student);
    }

    public void deleteStudent(Long id) throws StudentNotFoundException {
        var student = studentRepository.findById(id);
        if (student.isEmpty())
            throw new StudentNotFoundException();
        studentRepository.deleteById(id);
    }
}
