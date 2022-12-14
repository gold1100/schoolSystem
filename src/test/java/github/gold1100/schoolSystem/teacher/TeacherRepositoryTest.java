package github.gold1100.schoolSystem.teacher;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


@DataJpaTest
class TeacherRepositoryTest {

    @Autowired
    TeacherRepository underTest;
    static List<Teacher> data = new ArrayList<>();
    static final Pageable pageable = PageRequest.of(0,5);

    @BeforeAll
    static void setUp(){
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
        data = Arrays.asList(t1,t2,t3,t4);
    }

    @BeforeEach
    void setUpBeforeEach(){
        underTest.saveAll(data);
    }

    @AfterEach
    void tearDown(){
        underTest.deleteAll();
    }

    @Test
    void itShouldFindTeachersByTheirExactFirstName() {
        // when
        Page<Teacher> firstName = underTest.findByName("Patrycjusz", null, pageable);

        // then
        assertTrue(firstName.stream().allMatch(teacher -> teacher.getFirstName().equalsIgnoreCase("patrycjusz")));
        assertEquals(2, firstName.get().count());
    }

    @Test
    void itShouldFindTeachersByTheirExactLastName() {
        // when
        Page<Teacher> lastName = underTest.findByName(null, "Nowak", pageable);

        // then
        assertTrue(lastName.stream().allMatch(teacher -> teacher.getLastName().equalsIgnoreCase("Nowak")));
        assertEquals(3, lastName.get().count());
    }

    @Test
    void itShouldFindTeachersByTheirExactFirstNameAndLastName() {
        // when
        Page<Teacher> firstAndLastName = underTest.findByName("Czarek", "Nowak", pageable);

        // then
        assertTrue(firstAndLastName
                        .stream()
                        .allMatch(teacher -> teacher.getFirstName().equalsIgnoreCase("Czarek") && teacher.getLastName().equalsIgnoreCase("Nowak"))
        );
        assertEquals(2, firstAndLastName.get().count());
    }
}