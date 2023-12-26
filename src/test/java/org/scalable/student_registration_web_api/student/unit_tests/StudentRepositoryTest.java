package org.scalable.student_registration_web_api.student.unit_tests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.scalable.student_registration_web_api.student.Student;
import org.scalable.student_registration_web_api.student.StudentEntityDummyData;
import org.scalable.student_registration_web_api.student.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class StudentRepositoryTest {

    @Autowired
    private StudentRepository underTest;

    @AfterEach
    public void tearDown(){
        underTest.deleteAll();
    }

    @Test
    public void shouldCheckStudentExistsByEmail() {
        // Arrange
        Student student = StudentEntityDummyData.getStudent();

        underTest.save(student);

        // Act
        var exists = underTest.selectExistsEmail(student.getEmail());

        // Assert
        Assertions.assertTrue(exists);
    }

    @Test
    public void shouldCheckStudentDoesNotExistsByEmail() {
        // Arrange
        String email = StudentEntityDummyData.getStudent().getEmail();

        // Act
        var exists = underTest.selectExistsEmail(email);

        // Assert
        Assertions.assertFalse(exists);
    }
}