package org.scalable.student_registration_web_api.student;

public class StudentEntityDummyData {
    public static Student getStudent(){
        return new Student(
                "Ali Faisal",
                "alifaisalaslam@gmail.com",
                "17K-1234",
                "CS"
        );
    }

    public static Student getStudentWithId(){
        var student = getStudent();
        student.setId(1L);
        return student;
    }
}
