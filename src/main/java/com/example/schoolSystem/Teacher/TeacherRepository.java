package com.example.schoolSystem.Teacher;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    @Query( "SELECT t" +
            " FROM Teacher t" +
            " WHERE (?1 is null OR LOWER(t.firstName) = ?1)" +
            " AND (?2 is null OR LOWER(t.lastName) = ?2)"
    )
    Page<Teacher> findByName(String firstName, String lastName,
                                         Pageable pageable);
}
