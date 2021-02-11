package com.att.university.dao;

import com.att.university.Config;
import com.att.university.H2Config;
import com.att.university.entity.Faculty;
import com.att.university.entity.Group;
import com.att.university.entity.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {Config.class, H2Config.class})
class StudentDaoTest {
    @Autowired
    private DataSource dataSource;

    @Autowired
    private StudentDao studentDao;

    @BeforeEach
    void tearDown() {
        ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator();
        databasePopulator.addScripts(new ClassPathResource("Table.sql"), new ClassPathResource("testData.sql"));
        DatabasePopulatorUtils.execute(databasePopulator, dataSource);
    }

    @Test
    void findAllShouldReturnResultWhenDatabaseHaveStudents() {
        Group group = new Group(1, "GT-23", new Faculty(1, "School of Visual arts"));

        List<Student> expected = Arrays.asList(
                Student.builder()
                        .withId(1)
                        .withFirstName("Fedor")
                        .withLastName("Tolov")
                        .withEmail("tolof234@tmail.com")
                        .withPassword("password")
                        .withGroup(group)
                        .build(),
                Student.builder()
                        .withId(2)
                        .withFirstName("Anton")
                        .withLastName("Petrov")
                        .withEmail("p.anton@tmail.com")
                        .withPassword("password")
                        .withGroup(group)
                        .build()
        );

        assertEquals(expected, studentDao.findAll(1, studentDao.count()));
    }

    @Test
    void findByIdShouldReturnResultWhenDatabaseHaveStudents() {
        Group group = new Group(1, "GT-23", new Faculty(1, "School of Visual arts"));

        Student expected = Student.builder()
                .withId(1)
                .withFirstName("Fedor")
                .withLastName("Tolov")
                .withEmail("tolof234@tmail.com")
                .withPassword("password")
                .withGroup(group)
                .build();
        Optional<Student> actual = studentDao.findById(1);

        assertTrue(actual.isPresent());
        assertEquals(expected, actual.get());
    }

    @Test
    void countShouldReturnResultWhenDatabaseHaveStudents() {
        int expected = 2;

        assertEquals(expected, studentDao.count());
    }

    @Test
    void saveShouldReturnResultWhenDatabaseHaveStudents() {
        Group group = new Group(1, "GT-23", new Faculty(1, "School of Visual arts"));

        Student newStudent = Student.builder()
                .withFirstName("new")
                .withLastName("Tolov")
                .withEmail("tolof234@tmail.com")
                .withPassword("password")
                .withGroup(group)
                .build();

        int currentCount = studentDao.count();

        studentDao.save(newStudent);

        assertEquals(currentCount + 1, studentDao.count());
    }

    @Test
    void saveAllShouldReturnResultWhenDatabaseHaveStudents() {
        Group group = new Group(1, "GT-23", new Faculty(1, "School of Visual arts"));

        List<Student> newStudents = Arrays.asList(
                Student.builder()
                        .withFirstName("new")
                        .withLastName("Tolov")
                        .withEmail("tolof234@tmail.com")
                        .withPassword("password")
                        .withGroup(group)
                        .build(),
                Student.builder()
                        .withFirstName("new")
                        .withLastName("Tolov")
                        .withEmail("tolof234@tmail.com")
                        .withPassword("password")
                        .withGroup(group)
                        .build()
        );

        int currentCount = studentDao.count();
        studentDao.saveAll(newStudents);

        assertEquals(currentCount + 2, studentDao.count());
    }

    @Test
    void deleteByIdShouldReturnResultWhenDatabaseHaveStudents() {
        int currentCount = studentDao.count();
        studentDao.deleteById(1);

        assertEquals(currentCount - 1, studentDao.count());
    }

    @Test
    void updateShouldReturnResultWhenDatabaseHaveStudents() {
        Group group = new Group(1, "GT-23", new Faculty(1, "School of Visual arts"));

        Student newStudent = Student.builder()
                .withId(1)
                .withFirstName("update")
                .withLastName("update_l")
                .withEmail("tolof234@tmail.com")
                .withPassword("password")
                .withGroup(group)
                .build();
        studentDao.update(newStudent);

        Optional<Student> updateStudent = studentDao.findById(1);

        assertTrue(updateStudent.isPresent());
        assertEquals("update", updateStudent.get().getFirstName());
        assertEquals("update_l", updateStudent.get().getLastName());
    }
}
