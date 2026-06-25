package com.studentdbms.exception;

// OOP: Custom Exception class — extends RuntimeException
// Exception Handling: We use this instead of generic exceptions
public class StudentNotFoundException extends RuntimeException {

    public StudentNotFoundException(String message) {
        super(message);
    }

    public StudentNotFoundException(Long id) {
        super("Student not found with ID: " + id);
    }
}