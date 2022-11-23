package com.example.schoolSystem.Student;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long > {
    @Query( "SELECT s" +
            " FROM Student s" +
            " WHERE (?1 is null OR LOWER(s.firstName) = ?1)" +
            " AND (?2 is null OR LOWER(s.lastName) = ?2)"
    )
    Page<Student> findByName(String firstName, String lastName,
                             Pageable pageable);
}
