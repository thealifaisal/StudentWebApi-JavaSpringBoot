package org.scalable.amigoscodetests.student;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.fail;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class StudentIntegrationTest {

    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private MockMvc apiController;

    @AfterEach
    public void tearDown(){
        studentRepository.deleteAll();
    }

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

        var getRequest = get("http://localhost:8080/api/v1/students")
                .contentType(MediaType.APPLICATION_JSON);

        // Act
        var postAction = apiController.perform(postRequest);

        // Assert
        postAction.andExpect(status().isOk());

        // Assertion through GET API
        var getStudentJson = apiController.perform(getRequest).andReturn().getResponse().getContentAsString();
        Optional<List<Student>> students = jsonToObject(getStudentJson, new TypeReference<List<Student>>() {});
        Assertions.assertTrue(students.isPresent());
        Assertions.assertTrue(students.get().stream().anyMatch(s -> s.getEmail().equals(student.getEmail())));

        // Assertion through Repository
        var isStudentCreated = studentRepository.selectExistsEmail(student.getEmail());
        Assertions.assertTrue(isStudentCreated);
    }

    private <T> Optional<T> jsonToObject(String json, TypeReference<T> type){
        try{
            return Optional.of(new ObjectMapper().readValue(json, type));
        }
        catch (JsonProcessingException ex){
            fail("Failed to convert json to object.");
            return Optional.empty();
        }
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
