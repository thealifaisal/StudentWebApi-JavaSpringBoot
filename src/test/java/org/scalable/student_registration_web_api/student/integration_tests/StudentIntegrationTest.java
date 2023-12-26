package org.scalable.student_registration_web_api.student.integration_tests;

import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.scalable.student_registration_web_api.student.StudentDtoDummyData;
import org.scalable.student_registration_web_api.student.StudentRepository;
import org.scalable.student_registration_web_api.student.dtos.StudentDto;
import org.scalable.student_registration_web_api.utilities.mapper.JsonObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.List;
import java.util.Objects;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class StudentIntegrationTest {

    private final String STUDENT_API_URL = "http://localhost:8080/api/v1/students";
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private MockMvc apiController;

    @AfterEach
    public void tearDown(){
        studentRepository.deleteAll();
    }

    /**
     * create a student
     *  <br> - test if it is created
     *  <br> - check api controller response status is ok
     *  <br> - check repository if student is created with the email
     * @throws Exception testStudentCreation failed
     */
    @Test
    public void testStudentCreation() throws Exception {

        // Arrange
        var studentDto = StudentDtoDummyData.getStudent();
        var jsonStudent = JsonObjectMapper.objectToJson(studentDto);
        var postRequest = buildPostRequest(jsonStudent);
        var getRequest = buildGetRequest();

        // Act
        var postAction = apiController.perform(postRequest);

        // Assert
        // Via HttpStatus
        postAction.andExpect(status().isOk());

        // Via GET API
        var getStudentJson = apiController.perform(getRequest).andReturn().getResponse().getContentAsString();
        List<StudentDto> students = JsonObjectMapper.jsonToObject(getStudentJson, new TypeReference<List<StudentDto>>() {});
        Assertions.assertTrue(students.stream().anyMatch(s -> s.getEmail().equals(studentDto.getEmail())));

        // Via Repository
        var isStudentCreated = studentRepository.selectExistsEmail(studentDto.getEmail());
        Assertions.assertTrue(isStudentCreated);
    }

    /**
     * create a student
     *  <br> - test if it is deleted
     *  <br> - check api controller response status is ok
     *  <br> - check repository if student is deleted
     * @throws Exception testStudentDeletion failed
     */
    @Test
    public void testStudentDeletion() throws Exception {

        // Arrange
        var studentDto = StudentDtoDummyData.getStudent();
        var jsonStudent = JsonObjectMapper.objectToJson(studentDto);
        var postRequest = buildPostRequest(jsonStudent);
        var postAction = apiController.perform(postRequest);
        var deleteRequest = buildDeleteRequest(studentDto.getId());
        postAction.andExpect(status().isOk());

        // Act
        var deleteAction = apiController.perform(deleteRequest);

        // Assert
        // Via HttpStatus
        deleteAction.andExpect(status().isOk());

        // Via Repository
        var isStudentDeleted = !studentRepository.existsById(studentDto.getId());
        Assertions.assertTrue(isStudentDeleted);
    }

    private MockHttpServletRequestBuilder buildPostRequest(String jsonBody){
        return post(STUDENT_API_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(Objects.requireNonNull(jsonBody));
    }

    private MockHttpServletRequestBuilder buildDeleteRequest(Long id){
        return delete(String.format(STUDENT_API_URL + "/%s", id))
                .contentType(MediaType.APPLICATION_JSON);
    }

    private MockHttpServletRequestBuilder buildGetRequest(){
        return get(STUDENT_API_URL)
                .contentType(MediaType.APPLICATION_JSON);
    }
}
