package com.att.university.dao;

import com.att.university.H2Config;
import com.att.university.entity.Faculty;
import com.att.university.entity.Group;
import com.att.university.entity.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = H2Config.class)
class StudentDaoTest extends AbstractTest {
    @Autowired
    private DataSource dataSource;

    @Autowired
    private StudentDao studentDao;

    @BeforeEach
    void tearDown() {
        recreateDb(dataSource);
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

        assertThat(studentDao.findAll(1, studentDao.count())).isEqualTo(expected);
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

        assertThat(actual).isPresent().hasValue(expected);
    }

    @Test
    void countShouldReturnResultWhenDatabaseHaveStudents() {
        int expected = 2;

        assertThat(studentDao.count()).isEqualTo(expected);
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

        assertThat(studentDao.count()).isEqualTo(currentCount + 1);
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

        assertThat(studentDao.count()).isEqualTo(currentCount + 2);
    }

    @Test
    void deleteByIdShouldReturnResultWhenDatabaseHaveStudents() {
        int currentCount = studentDao.count();
        studentDao.deleteById(1);

        assertThat(studentDao.count()).isEqualTo(currentCount - 1);
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

        assertThat(updateStudent).isPresent();
        assertThat(updateStudent.get().getFirstName()).isEqualTo("update");
        assertThat(updateStudent.get().getLastName()).isEqualTo("update_l");
    }

    @Test
    void findByEmailShouldReturnResultWhenDatabaseHaveStudents() {
        Group group = new Group(1, "GT-23", new Faculty(1, "School of Visual arts"));

        Student expected = Student.builder()
                .withId(1)
                .withFirstName("Fedor")
                .withLastName("Tolov")
                .withEmail("tolof234@tmail.com")
                .withPassword("password")
                .withGroup(group)
                .build();
        Optional<Student> actual = studentDao.findByEmail("tolof234@tmail.com");

        assertThat(actual).isPresent().hasValue(expected);
    }
}
