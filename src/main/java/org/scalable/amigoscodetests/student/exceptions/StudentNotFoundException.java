package org.scalable.amigoscodetests.student.exceptions;

public class StudentNotFoundException extends Exception {
    private static final String STUDENT_NOT_FOUND_MESSAGE = "Student with the id does not exists.";

    public StudentNotFoundException() {
        super(STUDENT_NOT_FOUND_MESSAGE);
    }
}
