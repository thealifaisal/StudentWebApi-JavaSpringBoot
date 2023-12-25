package org.scalable.amigoscodetests.student;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.Assertions;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.scalable.amigoscodetests.student.exceptions.StudentAlreadyExistsException;
import org.scalable.amigoscodetests.student.exceptions.StudentNotFoundException;

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
        // arrange
        // act
        underTest.getAllStudents();
        // assert: verifies that studentRepository calls findAll() in studentService.getAllStudents()
        verify(studentRepository).findAll();
    }

    @Test
    void shouldAddStudentWhenStudentEmailDoesNotExist() throws StudentAlreadyExistsException {
        // arrange
        Student student = new Student(
                "Ali Faisal",
                "alifaisal@gmail.com",
                "K17-3791",
                "Computer Science");

        // act
        underTest.addStudent(student);

        // assert: verifies that studentRepository calls save(student) in studentService.addStudent(student)
        //         verifies that the passed student obj in studentService is same as student obj passed in studentRepository
        //         NOTE: obj comparison is done by reference, and for each of the fields in the obj
        ArgumentCaptor<Student> captor = ArgumentCaptor.forClass(Student.class);
        verify(studentRepository).save(captor.capture());
        Student capturedStudent = captor.getValue();
        Assertions.assertEquals(student, capturedStudent);
    }

    @Test
    void shouldThrowExceptionWhenStudentEmailAlreadyExists() {
        // arrange
        Student student = new Student(
                "Ali Faisal",
                "alifaisal@gmail.com",
                "K17-3791",
                "Computer Science");

        given(studentRepository.selectExistsEmail(anyString())).willReturn(true);

        // act
        // assert
        Assertions.assertThrows(StudentAlreadyExistsException.class, () -> underTest.addStudent(student));

        // studentRepository.save(student) will never get called, so ignoring its verification in the test
        verify(studentRepository, never()).save(any());
    }

    @Test
    void shouldDeleteWhenStudentWithIdExists() throws StudentNotFoundException, StudentAlreadyExistsException {
        // arrange
        Long studentId = 1L;
        Student student = new Student(
                studentId,
                "Ali Faisal",
                "alifaisal@gmail.com",
                "K17-3791",
                "Computer Science");
        given(studentRepository.findById(studentId)).willReturn(Optional.of(student));

        // act
        underTest.deleteStudent(studentId);

        // assert: verifies that studentRepository calls findById(studentId) in studentService.deleteStudent(studentId)
        //         verifies that the passed studentId obj in studentService is same as studentId obj passed in studentRepository
        //         NOTE: obj comparison is done by reference, and for each of the fields in the obj
        ArgumentCaptor<Long> captor = ArgumentCaptor.forClass(Long.class);
        verify(studentRepository).findById(captor.capture());
        Long capturedStudentId = captor.getValue();
        Assertions.assertEquals(studentId, capturedStudentId);

        // assert: verifies that studentRepository calls deleteById(studentId) in studentService.deleteStudent(studentId)
        //         verifies that the passed studentId obj in studentService is same as studentId obj passed in studentRepository
        //         NOTE: obj comparison is done by reference, and for each of the fields in the obj
        ArgumentCaptor<Long> captor2 = ArgumentCaptor.forClass(Long.class);
        verify(studentRepository).deleteById(captor2.capture());
        Long capturedStudentId2 = captor2.getValue();
        Assertions.assertEquals(studentId, capturedStudentId2);
    }

    @Test
    void shouldThrowExceptionWhenStudentWithIdDoesNotExists() {
        // arrange
        Long studentId = 1L;
        given(studentRepository.findById(anyLong())).willReturn(Optional.empty());

        // act
        // assert
        Assertions.assertThrows(StudentNotFoundException.class, () -> underTest.deleteStudent(studentId));

        // studentRepository.deleteById(studentId) will never get called, so ignoring its verification in the test
        verify(studentRepository, never()).deleteById(any());
    }
}