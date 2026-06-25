package com.studentdbms.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "students")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }

    // ── Constructors ──────────────────────────────
    public Student() {}

    public Student(Long id, String name, String email, Integer age,
                   String department, String course, String phone,
                   String address, Double cgpa, Integer yearOfStudy,
                   LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.age = age;
        this.department = department;
        this.course = course;
        this.phone = phone;
        this.address = address;
        this.cgpa = cgpa;
        this.yearOfStudy = yearOfStudy;
        this.createdAt = createdAt;
    }

    // ── Getters ───────────────────────────────────
    public Long getId()               { return id; }
    public String getName()           { return name; }
    public String getEmail()          { return email; }
    public Integer getAge()           { return age; }
    public String getDepartment()     { return department; }
    public String getCourse()         { return course; }
    public String getPhone()          { return phone; }
    public String getAddress()        { return address; }
    public Double getCgpa()           { return cgpa; }
    public Integer getYearOfStudy()   { return yearOfStudy; }
    public LocalDateTime getCreatedAt() { return createdAt; }

    // ── Setters ───────────────────────────────────
    public void setId(Long id)                     { this.id = id; }
    public void setName(String name)               { this.name = name; }
    public void setEmail(String email)             { this.email = email; }
    public void setAge(Integer age)                { this.age = age; }
    public void setDepartment(String department)   { this.department = department; }
    public void setCourse(String course)           { this.course = course; }
    public void setPhone(String phone)             { this.phone = phone; }
    public void setAddress(String address)         { this.address = address; }
    public void setCgpa(Double cgpa)               { this.cgpa = cgpa; }
    public void setYearOfStudy(Integer yearOfStudy){ this.yearOfStudy = yearOfStudy; }
    public void setCreatedAt(LocalDateTime createdAt){ this.createdAt = createdAt; }

    @Override
    public String toString() {
        return "Student{id=" + id + ", name='" + name + "', email='" + email + "'}";
    }
}