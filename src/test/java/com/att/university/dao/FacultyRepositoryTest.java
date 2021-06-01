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
class FacultyRepositoryTest {
    @Autowired
    private FacultyRepository facultyRepository;

    @Test
    void findAllShouldReturnResultWhenDatabaseHaveFaculties() {
        List<Faculty> expected = Arrays.asList(
                new Faculty(1, "School of Visual arts"),
                new Faculty(2, "Department of Geography"),
                new Faculty(3, "Department of Plant Science"),
                new Faculty(4, "Geography")
        );

        assertThat(facultyRepository.findAll()).isEqualTo(expected);
    }

    @Test
    void findByIdShouldReturnResultWhenDatabaseHaveFaculties() {
        Faculty expected = new Faculty(1, "School of Visual arts");
        Optional<Faculty> actual = facultyRepository.findById(1);

        assertThat(actual).isPresent().hasValue(expected);
    }

    @Test
    void countShouldReturnResultWhenDatabaseHaveFaculties() {
        int expected = 4;

        assertThat(facultyRepository.count()).isEqualTo(expected);
    }

    @Test
    @Transactional
    void saveShouldReturnResultWhenDatabaseHaveFaculties() {
        Faculty newFaculty = new Faculty(null, "new");
        long currentCount = facultyRepository.count();

        facultyRepository.save(newFaculty);

        assertThat(facultyRepository.count()).isEqualTo(currentCount + 1);
    }

    @Test
    @Transactional
    void saveAllShouldReturnResultWhenDatabaseHaveFaculties() {
        List<Faculty> newFaculties = Arrays.asList(
                new Faculty(null, "new"),
                new Faculty(null, "new")
        );

        long currentCount = facultyRepository.count();
        facultyRepository.saveAll(newFaculties);

        assertThat(facultyRepository.count()).isEqualTo(currentCount + 2);
    }

    @Test
    @Transactional
    void deleteByIdShouldReturnResultWhenDatabaseHaveFaculties() {
        long currentCount = facultyRepository.count();
        facultyRepository.deleteById(4);

        assertThat(facultyRepository.count()).isEqualTo(currentCount - 1);
    }

    @Test
    @Transactional
    void updateShouldReturnResultWhenDatabaseHaveFaculties() {
        Faculty newFaculty = new Faculty(1, "update");
        facultyRepository.save(newFaculty);

        Optional<Faculty> updateFaculty = facultyRepository.findById(1);

        assertThat(updateFaculty).isPresent();
        assertThat(updateFaculty.get().getName()).isEqualTo("update");
    }
}
