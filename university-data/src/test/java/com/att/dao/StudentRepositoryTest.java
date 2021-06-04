package com.att.dao;

import com.att.entity.Faculty;
import com.att.entity.Group;
import com.att.entity.Student;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = {"classpath:TestData.sql"})
class StudentRepositoryTest {
    @Autowired
    private StudentRepository studentRepository;

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
                        .build(),
                Student.builder()
                        .withId(3)
                        .withFirstName("Fedor")
                        .withLastName("Petrov")
                        .withEmail("anton@tmail.com")
                        .withPassword("password")
                        .withGroup(null)
                        .build()
        );

        assertThat(studentRepository.findAll()).isEqualTo(expected);
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
        Optional<Student> actual = studentRepository.findById(1);

        assertThat(actual).isPresent().hasValue(expected);
    }

    @Test
    void countShouldReturnResultWhenDatabaseHaveStudents() {
        int expected = 3;

        assertThat(studentRepository.count()).isEqualTo(expected);
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

        long currentCount = studentRepository.count();

        studentRepository.save(newStudent);

        assertThat(studentRepository.count()).isEqualTo(currentCount + 1);
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

        long currentCount = studentRepository.count();
        studentRepository.saveAll(newStudents);

        assertThat(studentRepository.count()).isEqualTo(currentCount + 2);
    }

    @Test
    void deleteByIdShouldReturnResultWhenDatabaseHaveStudents() {
        long currentCount = studentRepository.count();
        studentRepository.deleteById(3);

        assertThat(studentRepository.count()).isEqualTo(currentCount - 1);
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

        studentRepository.save(newStudent);

        Optional<Student> updateStudent = studentRepository.findById(1);

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
        Optional<Student> actual = studentRepository.findByEmail("tolof234@tmail.com");

        assertThat(actual).isPresent().hasValue(expected);
    }

    @SpringBootApplication
    @EntityScan("com.att.entity")
    static class TestConfiguration {
    }
}
