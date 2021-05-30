package com.att.university.dao;

import com.att.university.entity.Faculty;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@TestPropertySource("/application-test.properties")
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = {"classpath:TestData.sql"})
class FacultyDaoTest {
    @Autowired
    private FacultyDao facultyDao;

    @Test
    void findAllShouldReturnResultWhenDatabaseHaveFaculties() {
        List<Faculty> expected = Arrays.asList(
                new Faculty(1, "School of Visual arts"),
                new Faculty(2, "Department of Geography"),
                new Faculty(3, "Department of Plant Science"),
                new Faculty(4, "Geography")
        );

        assertThat(facultyDao.findAll(1, facultyDao.count())).isEqualTo(expected);
    }

    @Test
    void findByIdShouldReturnResultWhenDatabaseHaveFaculties() {
        Faculty expected = new Faculty(1, "School of Visual arts");
        Optional<Faculty> actual = facultyDao.findById(1);

        assertThat(actual).isPresent().hasValue(expected);
    }

    @Test
    void countShouldReturnResultWhenDatabaseHaveFaculties() {
        int expected = 4;

        assertThat(facultyDao.count()).isEqualTo(expected);
    }

    @Test
    @Transactional
    void saveShouldReturnResultWhenDatabaseHaveFaculties() {
        Faculty newFaculty = new Faculty(null, "new");
        int currentCount = facultyDao.count();

        facultyDao.save(newFaculty);

        assertThat(facultyDao.count()).isEqualTo(currentCount + 1);
    }

    @Test
    @Transactional
    void saveAllShouldReturnResultWhenDatabaseHaveFaculties() {
        List<Faculty> newFaculties = Arrays.asList(
                new Faculty(null, "new"),
                new Faculty(null, "new")
        );

        int currentCount = facultyDao.count();
        facultyDao.saveAll(newFaculties);

        assertThat(facultyDao.count()).isEqualTo(currentCount + 2);
    }

    @Test
    @Transactional
    void deleteByIdShouldReturnResultWhenDatabaseHaveFaculties() {
        int currentCount = facultyDao.count();
        facultyDao.deleteById(4);

        assertThat(facultyDao.count()).isEqualTo(currentCount - 1);
    }

    @Test
    @Transactional
    void updateShouldReturnResultWhenDatabaseHaveFaculties() {
        Faculty newFaculty = new Faculty(1, "update");
        facultyDao.update(newFaculty);

        Optional<Faculty> updateFaculty = facultyDao.findById(1);

        assertThat(updateFaculty).isPresent();
        assertThat(updateFaculty.get().getName()).isEqualTo("update");
    }
}
