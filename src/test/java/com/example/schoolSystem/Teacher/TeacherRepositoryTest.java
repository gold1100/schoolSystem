package com.example.schoolSystem.Teacher;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


@DataJpaTest
class TeacherRepositoryTest {

    @Autowired
    TeacherRepository underTest;

    @Test
    void itShouldReturnExactMatchedNames() {
        // given
        Teacher t1 = new Teacher();
        t1.setFirstName("Patrycjusz");
        t1.setLastName("Nowak");
        Teacher t2 = new Teacher();
        t2.setFirstName("Patrycjusz");
        t2.setLastName("Something");
        Teacher t3 = new Teacher();
        t3.setFirstName("Czarek");
        t3.setLastName("Nowak");
        Teacher t4 = new Teacher();
        t4.setFirstName("Czarek");
        t4.setLastName("Nowak");
        underTest.save(t1);
        underTest.save(t2);
        underTest.save(t3);
        underTest.save(t4);
        Pageable pageable = PageRequest.of(0,5);

        // when
        Page<Teacher> firstName = underTest.findByName("Patrycjusz", null, pageable);
        Page<Teacher> lastName = underTest.findByName(null, "Nowak", pageable);
        Page<Teacher> firstAndLastName = underTest.findByName("Czarek", "Nowak", pageable);

        // then
        assertTrue(firstName.stream().allMatch(teacher -> teacher.getFirstName().equalsIgnoreCase("patrycjusz")));
        assertEquals(2, firstName.get().count());
        assertTrue(lastName.stream().allMatch(teacher -> teacher.getLastName().equalsIgnoreCase("Nowak")));
        assertEquals(3, lastName.get().count());
        assertTrue(firstAndLastName.stream().allMatch(teacher ->
                teacher.getFirstName().equalsIgnoreCase("Czarek") && teacher.getLastName().equalsIgnoreCase("Nowak")));
        assertEquals(2, firstAndLastName.get().count());
    }

}