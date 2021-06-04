package com.att.dao;

import com.att.entity.AcademicRank;
import com.att.entity.ScienceDegree;
import com.att.entity.Teacher;
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
class TeacherRepositoryTest {
    @Autowired
    private TeacherRepository teacherRepository;

    @Test
    void findAllShouldReturnResultWhenDatabaseHaveTeachers() {
        AcademicRank academicRank = new AcademicRank(1, "Assistant Professor");
        ScienceDegree scienceDegree = new ScienceDegree(2, "Doctoral degree");

        List<Teacher> expected = Arrays.asList(
                Teacher.builder()
                        .withId(1)
                        .withFirstName("Fedor")
                        .withLastName("Tolov")
                        .withEmail("tolof234@tmail.com")
                        .withPassword("password")
                        .withAcademicRank(academicRank)
                        .withScienceDegree(scienceDegree)
                        .withLinkedin("https://link.ru")
                        .build(),
                Teacher.builder()
                        .withId(2)
                        .withFirstName("Alex")
                        .withLastName("Popov")
                        .withEmail("email234@tmail.com")
                        .withPassword("password")
                        .withAcademicRank(academicRank)
                        .withScienceDegree(scienceDegree)
                        .withLinkedin("https://link.ru")
                        .build(),
                Teacher.builder()
                        .withId(3)
                        .withFirstName("Dima")
                        .withLastName("Antipov")
                        .withEmail("ant213@tmail.com")
                        .withPassword("password")
                        .withAcademicRank(academicRank)
                        .withScienceDegree(scienceDegree)
                        .withLinkedin("https://link.ru")
                        .build()
        );

        assertThat(teacherRepository.findAll()).isEqualTo(expected);
    }

    @Test
    void findByIdShouldReturnResultWhenDatabaseHaveTeachers() {
        AcademicRank academicRank = new AcademicRank(1, "Assistant Professor");
        ScienceDegree scienceDegree = new ScienceDegree(2, "Doctoral degree");

        Teacher expected = Teacher.builder()
                .withId(1)
                .withFirstName("Fedor")
                .withLastName("Tolov")
                .withEmail("tolof234@tmail.com")
                .withPassword("password")
                .withAcademicRank(academicRank)
                .withScienceDegree(scienceDegree)
                .withLinkedin("https://link.ru")
                .build();

        Optional<Teacher> actual = teacherRepository.findById(1);

        assertThat(actual).isPresent().hasValue(expected);
    }

    @Test
    void countShouldReturnResultWhenDatabaseHaveTeachers() {
        int expected = 3;

        assertThat(teacherRepository.count()).isEqualTo(expected);
    }

    @Test
    void saveShouldReturnResultWhenDatabaseHaveTeachers() {
        AcademicRank academicRank = new AcademicRank(1, "Assistant Professor");
        ScienceDegree scienceDegree = new ScienceDegree(2, "Doctoral degree");

        Teacher newTeacher = Teacher.builder()
                .withFirstName("new")
                .withLastName("Tolov")
                .withEmail("toloffsdf234@tmail.com")
                .withPassword("password")
                .withAcademicRank(academicRank)
                .withScienceDegree(scienceDegree)
                .withLinkedin("https://link.ru")
                .build();

        long currentCount = teacherRepository.count();

        teacherRepository.save(newTeacher);

        assertThat(teacherRepository.count()).isEqualTo(currentCount + 1);
    }

    @Test
    void saveAllShouldReturnResultWhenDatabaseHaveTeachers() {
        AcademicRank academicRank = new AcademicRank(1, "Assistant Professor");
        ScienceDegree scienceDegree = new ScienceDegree(2, "Doctoral degree");

        List<Teacher> newTeachers = Arrays.asList(
                Teacher.builder()
                        .withFirstName("new")
                        .withLastName("Tolov")
                        .withEmail("tolof234@tmail.com")
                        .withPassword("password")
                        .withAcademicRank(academicRank)
                        .withScienceDegree(scienceDegree)
                        .withLinkedin("https://link.ru")
                        .build(),
                Teacher.builder()
                        .withFirstName("new")
                        .withLastName("Popov")
                        .withEmail("email234@tmail.com")
                        .withPassword("password")
                        .withAcademicRank(academicRank)
                        .withScienceDegree(scienceDegree)
                        .withLinkedin("https://link.ru")
                        .build()
        );

        long currentCount = teacherRepository.count();
        teacherRepository.saveAll(newTeachers);

        assertThat(teacherRepository.count()).isEqualTo(currentCount + 2);
    }

    @Test
    void deleteByIdShouldReturnResultWhenDatabaseHaveTeachers() {
        long currentCount = teacherRepository.count();
        teacherRepository.deleteById(3);

        assertThat(teacherRepository.count()).isEqualTo(currentCount - 1);
    }

    @Test
    void updateShouldReturnResultWhenDatabaseHaveTeachers() {
        AcademicRank academicRank = new AcademicRank(1, "Assistant Professor");
        ScienceDegree scienceDegree = new ScienceDegree(2, "Doctoral degree");

        Teacher newTeacher = Teacher.builder()
                .withId(1)
                .withFirstName("update")
                .withLastName("update_l")
                .withEmail("tolof234@tmail.com")
                .withPassword("password")
                .withAcademicRank(academicRank)
                .withScienceDegree(scienceDegree)
                .withLinkedin("https://link.ru")
                .build();

        teacherRepository.save(newTeacher);

        Optional<Teacher> updateTeacher = teacherRepository.findById(1);

        assertThat(updateTeacher).isPresent();
        assertThat(updateTeacher.get().getFirstName()).isEqualTo("update");
        assertThat(updateTeacher.get().getLastName()).isEqualTo("update_l");
    }

    @Test
    void findByEmailShouldReturnResultWhenDatabaseHaveTeachers() {
        AcademicRank academicRank = new AcademicRank(1, "Assistant Professor");
        ScienceDegree scienceDegree = new ScienceDegree(2, "Doctoral degree");

        Teacher expected = Teacher.builder()
                .withId(1)
                .withFirstName("Fedor")
                .withLastName("Tolov")
                .withEmail("tolof234@tmail.com")
                .withPassword("password")
                .withAcademicRank(academicRank)
                .withScienceDegree(scienceDegree)
                .withLinkedin("https://link.ru")
                .build();

        Optional<Teacher> actual = teacherRepository.findByEmail("tolof234@tmail.com");

        assertThat(actual).isPresent().hasValue(expected);
    }

    @SpringBootApplication
    @EntityScan("com.att.entity")
    static class TestConfiguration {
    }
}
