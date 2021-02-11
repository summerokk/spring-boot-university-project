package com.att.university.dao;

import com.att.university.Config;
import com.att.university.H2Config;
import com.att.university.entity.AcademicRank;
import com.att.university.entity.ScienceDegree;
import com.att.university.entity.Teacher;
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
class TeacherDaoTest {
    @Autowired
    private DataSource dataSource;

    @Autowired
    private TeacherDao teacherDao;

    @BeforeEach
    void tearDown() {
        ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator();
        databasePopulator.addScripts(new ClassPathResource("Table.sql"), new ClassPathResource("testData.sql"));
        DatabasePopulatorUtils.execute(databasePopulator, dataSource);
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

        assertEquals(expected, teacherDao.findAll(1, teacherDao.count()));
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

        assertTrue(actual.isPresent());
        assertEquals(expected, actual.get());
    }

    @Test
    void countShouldReturnResultWhenDatabaseHaveTeachers() {
        int expected = 2;

        assertEquals(expected, teacherDao.count());
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

        assertEquals(currentCount + 1, teacherDao.count());
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

        assertEquals(currentCount + 2, teacherDao.count());
    }

    @Test
    void deleteByIdShouldReturnResultWhenDatabaseHaveTeachers() {
        int currentCount = teacherDao.count();
        teacherDao.deleteById(1);

        assertEquals(currentCount - 1, teacherDao.count());
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

        assertTrue(updateTeacher.isPresent());
        assertEquals("update", updateTeacher.get().getFirstName());
        assertEquals("update_l", updateTeacher.get().getLastName());
    }
}
