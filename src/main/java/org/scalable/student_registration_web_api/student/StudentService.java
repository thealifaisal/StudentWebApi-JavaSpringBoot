package org.scalable.student_registration_web_api.student;

import lombok.AllArgsConstructor;
import org.scalable.student_registration_web_api.student.dtos.StudentDto;
import org.scalable.student_registration_web_api.student.exceptions.StudentAlreadyExistsException;
import org.scalable.student_registration_web_api.student.exceptions.StudentNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public List<StudentDto> getAllStudents() {
        return studentRepository.findAll().stream().map(Student::toDto).toList();
    }

    public void addStudent(StudentDto studentDto) throws StudentAlreadyExistsException {
        var studentExists = studentRepository.selectExistsEmail(studentDto.getEmail());
        if (studentExists)
            throw new StudentAlreadyExistsException();
        var student = studentDto.toEntity();
        studentRepository.save(student);
    }

    public void deleteStudent(Long id) throws StudentNotFoundException {
        var student = studentRepository.findById(id);
        if (student.isEmpty())
            throw new StudentNotFoundException();
        studentRepository.deleteById(id);
    }
}
