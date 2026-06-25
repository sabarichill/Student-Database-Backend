package com.studentdbms.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

// OOP: This class is an Entity (maps to DB table)
// Hibernate maps this Java class → MySQL table automatically

@Entity                         // Hibernate annotation: this is a DB table
@Table(name = "students")       // Maps to "students" table in MySQL
@Data                           // Lombok: auto-generates getters, setters, toString
@NoArgsConstructor              // Lombok: generates default constructor
@AllArgsConstructor             // Lombok: generates all-args constructor
public class Student {

    @Id                                                        // Primary Key
    @GeneratedValue(strategy = GenerationType.IDENTITY)        // Auto-increment
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    private Integer age;
    private String department;
    private String course;
    private String phone;
    private String address;
    private Double cgpa;

    @Column(name = "year_of_study")
    private Integer yearOfStudy;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    // OOP: Lifecycle method - auto-set timestamp on save
    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }
}