package org.scalable.amigoscodetests.student.exceptions;

public class StudentAlreadyExistsException extends Exception {
    private static final String STUDENT_ALREADY_EXISTS_MESSAGE = "Student with the email already exists.";

    public StudentAlreadyExistsException() {
        super(STUDENT_ALREADY_EXISTS_MESSAGE);
    }
}
