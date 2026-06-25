package com.studentdbms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication  // Enables auto-configuration, component scanning
public class StudentDbmsApplication {
    public static void main(String[] args) {
        SpringApplication.run(StudentDbmsApplication.class, args);
        System.out.println("✅ Student DBMS Backend is running on http://localhost:8080");
    }
}