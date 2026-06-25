package com.studentdbms.repository;

import com.studentdbms.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

// JpaRepository gives us free CRUD methods:
// save(), findAll(), findById(), deleteById(), count(), etc.
// This is the Collection Framework equivalent in Spring — like a smart List

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    // Custom query: search by name (case-insensitive)
    List<Student> findByNameContainingIgnoreCase(String name);

    // Search by department
    List<Student> findByDepartment(String department);

    // Search by email
    Optional<Student> findByEmail(String email);

    // Search by year of study
    List<Student> findByYearOfStudy(Integer year);

    // Custom JPQL query: search across multiple fields
    @Query("SELECT s FROM Student s WHERE " +
           "LOWER(s.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(s.department) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(s.course) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Student> searchByKeyword(@Param("keyword") String keyword);

    // Get students sorted by CGPA descending
    List<Student> findAllByOrderByCgpaDesc();

    // Get students by department sorted by name
    List<Student> findByDepartmentOrderByNameAsc(String department);
}