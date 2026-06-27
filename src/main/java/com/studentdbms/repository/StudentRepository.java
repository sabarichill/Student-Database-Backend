package com.studentdbms.repository;

import com.studentdbms.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    // Search by name
    List<Student> findByNameContainingIgnoreCase(String name);

    // Search by department
    List<Student> findByDepartment(String department);

    // Search by email
    Optional<Student> findByEmail(String email);

    // Search by yop (year of passing)
    List<Student> findByYop(Integer yop);

    // Search across multiple fields
    @Query("SELECT s FROM Student s WHERE " +
           "LOWER(s.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(s.department) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(s.course) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Student> searchByKeyword(@Param("keyword") String keyword);

    // Sorted by CGPA
    List<Student> findAllByOrderByCgpaDesc();

    // By department sorted by name
    List<Student> findByDepartmentOrderByNameAsc(String department);
}
