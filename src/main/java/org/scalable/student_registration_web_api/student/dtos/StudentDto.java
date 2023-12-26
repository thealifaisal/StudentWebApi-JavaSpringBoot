package org.scalable.student_registration_web_api.student.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.scalable.student_registration_web_api.student.Student;


@Getter
@AllArgsConstructor
public class StudentDto {
    private Long id;
    private String name;
    private String email;
    private String rollNumber;
    private String department;

    public StudentDto(){
    }

    public Student toEntity(){
        return new Student(
                id,
                name,
                email,
                rollNumber,
                department
        );
    }
}
