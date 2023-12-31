package org.scalable.student_registration_web_api.student;

import javax.persistence.*;
import lombok.Getter;
import org.scalable.student_registration_web_api.student.dtos.StudentDto;

@Getter
@Entity(name = "student_table")
public class Student {
    public Student() {
    }

    public Student(Long id, String name, String email, String rollNumber, String department){
        this.id = id;
        this.name = name;
        this.email = email;
        this.rollNumber = rollNumber;
        this.department = department;
    }

    @Id
    @GeneratedValue(generator="student_seq", strategy = GenerationType.IDENTITY)
    @SequenceGenerator(name="student_seq", allocationSize=1)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "roll_number", unique = true, nullable = false)
    private String rollNumber;

    @Column(name = "department", nullable = false)
    private String department;

    public StudentDto toDto(){
        return new StudentDto(
                id,
                name,
                email,
                rollNumber,
                department
        );
    }
}
