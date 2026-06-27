package com.studentdbms.service;

import com.studentdbms.exception.StudentNotFoundException;
import com.studentdbms.model.Student;
import com.studentdbms.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

// OOP: Service class — handles all business logic
// Collection Framework: We use List, ArrayList, Collections, Comparator

@Service
public class StudentService {

    @Autowired  // Dependency Injection (OOP concept)
    private StudentRepository repository;

    // ─── CREATE ──────────────────────────────────────────────
    public Student createStudent(Student student) {
        return repository.save(student);
    }

    // ─── READ ALL ────────────────────────────────────────────
    public List<Student> getAllStudents() {
        return repository.findAll();
    }

    // ─── READ ONE ────────────────────────────────────────────
    public Student getStudentById(Long id) {
        // Exception Handling: throws custom exception if not found
        return repository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException(id));
    }

    // ─── UPDATE ──────────────────────────────────────────────
    public Student updateStudent(Long id, Student updatedStudent) {
        Student existing = getStudentById(id); // throws if not found

        // OOP: Encapsulation — updating only changed fields
        existing.setName(updatedStudent.getName());
        existing.setEmail(updatedStudent.getEmail());
        existing.setAge(updatedStudent.getAge());
        existing.setDepartment(updatedStudent.getDepartment());
        existing.setCourse(updatedStudent.getCourse());
        existing.setPhone(updatedStudent.getPhone());
        existing.setAddress(updatedStudent.getAddress());
        existing.setCgpa(updatedStudent.getCgpa());
        existing.setYearOfStudy(updatedStudent.getYearOfStudy());

        return repository.save(existing);
    }

    // ─── DELETE ──────────────────────────────────────────────
    public String deleteStudent(Long id) {
        Student student = getStudentById(id); // throws if not found
        repository.deleteById(id);
        return "Student '" + student.getName() + "' deleted successfully!";
    }

    // ─── SEARCH ──────────────────────────────────────────────
    public List<Student> searchStudents(String keyword) {
        return repository.searchByKeyword(keyword);
    }

    // ─── SORT (Collection Framework) ─────────────────────────
    public List<Student> getSortedStudents(String sortBy, String order) {
        // Collection Framework: ArrayList + Comparator
        List<Student> students = new ArrayList<>(repository.findAll());

        Comparator<Student> comparator = switch (sortBy) {
            case "name"        -> Comparator.comparing(Student::getName, String.CASE_INSENSITIVE_ORDER);
            case "cgpa"        -> Comparator.comparingDouble(s -> s.getCgpa() != null ? s.getCgpa() : 0.0);
            case "age"         -> Comparator.comparingInt(s -> s.getAge() != null ? s.getAge() : 0);
            case "department"  -> Comparator.comparing(Student::getDepartment, String.CASE_INSENSITIVE_ORDER);
            case "yop" -> Comparator.comparingInt(s -> s.getYop() != null ? s.getYop() : 0);
            default            -> Comparator.comparing(Student::getId);
        };

        if ("desc".equalsIgnoreCase(order)) {
            comparator = comparator.reversed();
        }

        students.sort(comparator);
        return students;
    }

    // ─── FILTER BY DEPARTMENT ────────────────────────────────
    public List<Student> getByDepartment(String department) {
        return repository.findByDepartment(department);
    }

    // ─── COUNT ───────────────────────────────────────────────
    public long getTotalCount() {
        return repository.count();
    }

    // ─── GET ALL DEPARTMENTS ─────────────────────────────────
    public List<String> getAllDepartments() {
        List<Student> all = repository.findAll();
        // Collection Framework: using ArrayList + loop
        List<String> departments = new ArrayList<>();
        for (Student s : all) {
            if (s.getDepartment() != null && !departments.contains(s.getDepartment())) {
                departments.add(s.getDepartment());
            }
        }
        Collections.sort(departments);
        return departments;
    }
}
