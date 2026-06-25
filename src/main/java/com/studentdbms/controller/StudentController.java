package com.studentdbms.controller;

import com.studentdbms.model.Student;
import com.studentdbms.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

// @RestController = @Controller + @ResponseBody (returns JSON automatically)
// @CrossOrigin = allows React (port 3000) to call this backend (port 8080)

@RestController
@RequestMapping("/api/students")
@CrossOrigin(origins = "http://localhost:3000")
public class StudentController {

    @Autowired
    private StudentService service;

    // POST /api/students — Create new student
    @PostMapping
    public ResponseEntity<Student> createStudent(@RequestBody Student student) {
        Student saved = service.createStudent(student);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    // GET /api/students — Get all students
    @GetMapping
    public ResponseEntity<List<Student>> getAllStudents() {
        return ResponseEntity.ok(service.getAllStudents());
    }

    // GET /api/students/1 — Get student by ID
    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getStudentById(id));
    }

    // PUT /api/students/1 — Update student
    @PutMapping("/{id}")
    public ResponseEntity<Student> updateStudent(
            @PathVariable Long id,
            @RequestBody Student student) {
        return ResponseEntity.ok(service.updateStudent(id, student));
    }

    // DELETE /api/students/1 — Delete student
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteStudent(@PathVariable Long id) {
        return ResponseEntity.ok(service.deleteStudent(id));
    }

    // GET /api/students/search?keyword=arjun — Search
    @GetMapping("/search")
    public ResponseEntity<List<Student>> searchStudents(@RequestParam String keyword) {
        return ResponseEntity.ok(service.searchStudents(keyword));
    }

    // GET /api/students/sort?sortBy=cgpa&order=desc — Sort
    @GetMapping("/sort")
    public ResponseEntity<List<Student>> getSorted(
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "asc") String order) {
        return ResponseEntity.ok(service.getSortedStudents(sortBy, order));
    }

    // GET /api/students/filter?department=CSE
    @GetMapping("/filter")
    public ResponseEntity<List<Student>> filterByDepartment(@RequestParam String department) {
        return ResponseEntity.ok(service.getByDepartment(department));
    }

    // GET /api/students/count
    @GetMapping("/count")
    public ResponseEntity<Long> getCount() {
        return ResponseEntity.ok(service.getTotalCount());
    }

    // GET /api/students/departments
    @GetMapping("/departments")
    public ResponseEntity<List<String>> getDepartments() {
        return ResponseEntity.ok(service.getAllDepartments());
    }
}