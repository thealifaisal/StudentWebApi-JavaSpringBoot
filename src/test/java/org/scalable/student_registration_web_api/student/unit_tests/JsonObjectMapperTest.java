package org.scalable.student_registration_web_api.student.unit_tests;

import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.scalable.student_registration_web_api.student.StudentDtoDummyData;
import org.scalable.student_registration_web_api.student.dtos.StudentDto;
import org.scalable.student_registration_web_api.utilities.mapper.JsonObjectMapper;
import org.scalable.student_registration_web_api.utilities.mapper.JsonToObjectMappingException;
import org.scalable.student_registration_web_api.utilities.mapper.ObjectToJsonMappingException;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class JsonObjectMapperTest {

    @Test
    public void shouldReturnValidJsonFromObject() throws ObjectToJsonMappingException {

        // Arrange
        var input = StudentDtoDummyData.getStudent();
        var expectedOutput = "{\"id\":1,\"name\":\"Ali Faisal\",\"email\":\"alifaisalaslam@gmail.com\",\"rollNumber\":\"17K-1234\",\"department\":\"CS\"}";

        // Act
        var output = JsonObjectMapper.objectToJson(input);

        // Assert
        Assertions.assertEquals(expectedOutput, output);
    }

    @Test
    public void shouldReturnValidObjectFromJson() throws JsonToObjectMappingException {

        // Arrange
        var input = "{\"id\":1,\"name\":\"Ali Faisal\",\"email\":\"alifaisalaslam@gmail.com\",\"rollNumber\":\"17K-1234\",\"department\":\"CS\"}";
        var inputType = new TypeReference<StudentDto>() {};
        var expectedOutput = StudentDtoDummyData.getStudent();

        // Act
        var output = JsonObjectMapper.jsonToObject(input, inputType);

        // Assert
        Assertions.assertEquals(expectedOutput.getId(), output.getId());
        Assertions.assertEquals(expectedOutput.getName(), output.getName());
        Assertions.assertEquals(expectedOutput.getEmail(), output.getEmail());
        Assertions.assertEquals(expectedOutput.getRollNumber(), output.getRollNumber());
        Assertions.assertEquals(expectedOutput.getDepartment(), output.getDepartment());
    }

}
