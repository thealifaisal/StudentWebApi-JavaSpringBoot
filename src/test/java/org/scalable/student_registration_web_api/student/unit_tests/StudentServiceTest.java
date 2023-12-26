package org.scalable.student_registration_web_api.student.unit_tests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.scalable.student_registration_web_api.student.Student;
import org.scalable.student_registration_web_api.student.StudentDtoDummyData;
import org.scalable.student_registration_web_api.student.StudentRepository;
import org.scalable.student_registration_web_api.student.StudentService;
import org.scalable.student_registration_web_api.student.exceptions.StudentAlreadyExistsException;
import org.scalable.student_registration_web_api.student.exceptions.StudentNotFoundException;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;
    private StudentService underTest;


    @BeforeEach
    void setUp() {
        underTest = new StudentService(studentRepository);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void shouldGetAllStudents() {
        // Arrange
        // Act
        underTest.getAllStudents();
        // Assert: verifies that studentRepository calls findAll() in studentService.getAllStudents()
        verify(studentRepository).findAll();
    }

    @Test
    void shouldAddStudentWhenStudentEmailDoesNotExist() throws StudentAlreadyExistsException {
        // Arrange
        var studentDto = StudentDtoDummyData.getStudent();

        // Act
        underTest.addStudent(studentDto);

        // Assert: verifies that studentRepository calls save(student) in studentService.addStudent(student)
        //         verifies that the passed student obj in studentService is same as student obj passed in studentRepository
        //         NOTE: obj comparison is done by reference, and for each of the fields in the obj
        ArgumentCaptor<Student> captor = ArgumentCaptor.forClass(Student.class);
        verify(studentRepository).save(captor.capture());
        Student capturedStudent = captor.getValue();
        Assertions.assertEquals(studentDto.getName(), capturedStudent.getName());
        Assertions.assertEquals(studentDto.getEmail(), capturedStudent.getEmail());
        Assertions.assertEquals(studentDto.getRollNumber(), capturedStudent.getRollNumber());
        Assertions.assertEquals(studentDto.getDepartment(), capturedStudent.getDepartment());
    }

    @Test
    void shouldThrowExceptionWhenStudentEmailAlreadyExists() {
        // Arrange
        var studentDto = StudentDtoDummyData.getStudent();

        given(studentRepository.selectExistsEmail(anyString())).willReturn(true);

        // Act
        // Assert
        Assertions.assertThrows(StudentAlreadyExistsException.class, () -> underTest.addStudent(studentDto));

        // studentRepository.save(student) will never get called, so ignoring its verification in the test
        verify(studentRepository, never()).save(any());
    }

    @Test
    void shouldDeleteWhenStudentWithIdExists() throws StudentNotFoundException {
        // Arrange
        var studentDto = StudentDtoDummyData.getStudent();
        given(studentRepository.findById(studentDto.getId())).willReturn(Optional.of(studentDto.toEntity()));

        // Act
        underTest.deleteStudent(studentDto.getId());

        // Assert: verifies that studentRepository calls findById(studentId) in studentService.deleteStudent(studentId)
        //         verifies that the passed studentId obj in studentService is same as studentId obj passed in studentRepository
        //         NOTE: obj comparison is done by reference, and for each of the fields in the obj
        ArgumentCaptor<Long> captor = ArgumentCaptor.forClass(Long.class);
        verify(studentRepository).findById(captor.capture());
        Long capturedStudentId = captor.getValue();
        Assertions.assertEquals(studentDto.getId(), capturedStudentId);

        // Assert: verifies that studentRepository calls deleteById(studentId) in studentService.deleteStudent(studentId)
        //         verifies that the passed studentId obj in studentService is same as studentId obj passed in studentRepository
        //         NOTE: obj comparison is done by reference, and for each of the fields in the obj
        ArgumentCaptor<Long> captor2 = ArgumentCaptor.forClass(Long.class);
        verify(studentRepository).deleteById(captor2.capture());
        Long capturedStudentId2 = captor2.getValue();
        Assertions.assertEquals(studentDto.getId(), capturedStudentId2);
    }

    @Test
    void shouldThrowExceptionWhenStudentWithIdDoesNotExists() {
        // Arrange
        Long studentId = StudentDtoDummyData.getStudent().getId();
        given(studentRepository.findById(anyLong())).willReturn(Optional.empty());

        // Act
        // Assert
        Assertions.assertThrows(StudentNotFoundException.class, () -> underTest.deleteStudent(studentId));

        // studentRepository.deleteById(studentId) will never get called, so ignoring its verification in the test
        verify(studentRepository, never()).deleteById(any());
    }
}
