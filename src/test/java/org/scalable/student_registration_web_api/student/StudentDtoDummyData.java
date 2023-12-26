package org.scalable.student_registration_web_api.student;

import org.scalable.student_registration_web_api.student.dtos.StudentDto;

public class StudentDtoDummyData {
    public static StudentDto getStudent(){
        return new StudentDto(
                1L,
                "Ali Faisal",
                "alifaisalaslam@gmail.com",
                "17K-1234",
                "CS"
        );
    }
}
