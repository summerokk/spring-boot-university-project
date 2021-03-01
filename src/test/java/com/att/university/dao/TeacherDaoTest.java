package com.att.university.dao;

import com.att.university.H2Config;
import com.att.university.entity.AcademicRank;
import com.att.university.entity.ScienceDegree;
import com.att.university.entity.Teacher;
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
class TeacherDaoTest extends AbstractTest {
    @Autowired
    private DataSource dataSource;

    @Autowired
    private TeacherDao teacherDao;

    @BeforeEach
    void tearDown() {
        recreateDb(dataSource);
    }

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
                        .build()
        );

        assertThat(teacherDao.findAll(1, teacherDao.count())).isEqualTo(expected);
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

        Optional<Teacher> actual = teacherDao.findById(1);

        assertThat(actual).isPresent().hasValue(expected);
    }

    @Test
    void countShouldReturnResultWhenDatabaseHaveTeachers() {
        int expected = 2;

        assertThat(teacherDao.count()).isEqualTo(expected);
    }

    @Test
    void saveShouldReturnResultWhenDatabaseHaveTeachers() {
        AcademicRank academicRank = new AcademicRank(1, "Assistant Professor");
        ScienceDegree scienceDegree = new ScienceDegree(2, "Doctoral degree");

        Teacher newTeacher = Teacher.builder()
                .withId(1)
                .withFirstName("new")
                .withLastName("Tolov")
                .withEmail("toloffsdf234@tmail.com")
                .withPassword("password")
                .withAcademicRank(academicRank)
                .withScienceDegree(scienceDegree)
                .withLinkedin("https://link.ru")
                .build();

        int currentCount = teacherDao.count();

        teacherDao.save(newTeacher);

        assertThat(teacherDao.count()).isEqualTo(currentCount + 1);
    }

    @Test
    void saveAllShouldReturnResultWhenDatabaseHaveTeachers() {
        AcademicRank academicRank = new AcademicRank(1, "Assistant Professor");
        ScienceDegree scienceDegree = new ScienceDegree(2, "Doctoral degree");

        List<Teacher> newTeachers = Arrays.asList(
                Teacher.builder()
                        .withId(1)
                        .withFirstName("new")
                        .withLastName("Tolov")
                        .withEmail("tolof234@tmail.com")
                        .withPassword("password")
                        .withAcademicRank(academicRank)
                        .withScienceDegree(scienceDegree)
                        .withLinkedin("https://link.ru")
                        .build(),
                Teacher.builder()
                        .withId(2)
                        .withFirstName("new")
                        .withLastName("Popov")
                        .withEmail("email234@tmail.com")
                        .withPassword("password")
                        .withAcademicRank(academicRank)
                        .withScienceDegree(scienceDegree)
                        .withLinkedin("https://link.ru")
                        .build()
        );

        int currentCount = teacherDao.count();
        teacherDao.saveAll(newTeachers);

        assertThat(teacherDao.count()).isEqualTo(currentCount + 2);
    }

    @Test
    void deleteByIdShouldReturnResultWhenDatabaseHaveTeachers() {
        int currentCount = teacherDao.count();
        teacherDao.deleteById(1);

        assertThat(teacherDao.count()).isEqualTo(currentCount - 1);
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

        teacherDao.update(newTeacher);

        Optional<Teacher> updateTeacher = teacherDao.findById(1);

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

        Optional<Teacher> actual = teacherDao.findByEmail("tolof234@tmail.com");

        assertThat(actual).isPresent().hasValue(expected);
    }
}
