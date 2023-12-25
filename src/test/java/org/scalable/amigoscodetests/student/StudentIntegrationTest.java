package org.scalable.amigoscodetests.student;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.fail;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class StudentIntegrationTest {

    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private MockMvc apiController;

    @Test
    public void testStudentCreation() throws Exception {
        // create a student
        // test if it is created
        //  - check api controller response status is ok
        //  - check repository if student is created with the email

        // Arrange
        var student = new Student(
                "Ali Faisal",
                "alifaisal@gmail.com",
                "17K-3791",
                "CS"
        );

        var jsonStudent = objectToJson(student);
        if (jsonStudent.isEmpty())
            throw new Exception("Failed to convert student to json.");

        var postRequest = post("http://localhost:8080/api/v1/students")
                .contentType(MediaType.APPLICATION_JSON)
                .content(Objects.requireNonNull(jsonStudent.get()));

        // Act
        var postAction = apiController.perform(postRequest);

        // Assert
        postAction.andExpect(status().isOk());

        var isStudentCreated = studentRepository.selectExistsEmail(student.getEmail());
        Assertions.assertTrue(isStudentCreated);
    }

    private Optional<String> objectToJson(Object object){
        try{
            var json = new ObjectMapper().writeValueAsString(object);
            return Optional.of(json);
        }
        catch (JsonProcessingException ex){
            fail("Failed to convert object to json.");
            return Optional.empty();
        }
    }
}
