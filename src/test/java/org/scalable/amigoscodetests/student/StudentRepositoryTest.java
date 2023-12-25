package org.scalable.amigoscodetests.student;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
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
        //arrange
        Student student = new Student(
                "Ali Faisal",
                "alifaisal@gmail.com",
                "K17-3791",
                "Computer Science");

        underTest.save(student);

        //act
        var exists = underTest.selectExistsEmail(student.getEmail());

        //assert
        Assertions.assertTrue(exists);
    }

    @Test
    public void shouldCheckStudentDoesNotExistsByEmail() {
        //arrange
        String email = "alifaisal@gmail.com";

        //act
        var exists = underTest.selectExistsEmail(email);

        //assert
        Assertions.assertFalse(exists);
    }
}