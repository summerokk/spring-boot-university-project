package com.att.university.dao;

import com.att.university.Config;
import com.att.university.H2Config;
import com.att.university.entity.Faculty;
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
class FacultyDaoTest {
    @Autowired
    private DataSource dataSource;

    @Autowired
    private FacultyDao FacultyDao;

    @BeforeEach
    void tearDown() {
        ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator();
        databasePopulator.addScripts(new ClassPathResource("tables.sql"), new ClassPathResource("startData.sql"));
        DatabasePopulatorUtils.execute(databasePopulator, dataSource);
    }

    @Test
    void findAllShouldReturnResultWhenDatabaseHaveFaculties() {
        List<Faculty> expected = Arrays.asList(
                new Faculty(1, "School of Visual arts"),
                new Faculty(2, "Department of Geography"),
                new Faculty(3, "Department of Plant Science")
        );

        assertEquals(expected, FacultyDao.findAll(1, FacultyDao.count()));
    }

    @Test
    void findByIdShouldReturnResultWhenDatabaseHaveFaculties() {
        Faculty expected = new Faculty(1, "School of Visual arts");
        Optional<Faculty> actual = FacultyDao.findById(1);

        assertTrue(actual.isPresent());
        assertEquals(expected, actual.get());
    }

    @Test
    void countShouldReturnResultWhenDatabaseHaveFaculties() {
        int expected = 3;

        assertEquals(expected, FacultyDao.count());
    }

    @Test
    void saveShouldReturnResultWhenDatabaseHaveFaculties() {
        Faculty newFaculty = new Faculty(null, "new");
        int currentCount = FacultyDao.count();

        FacultyDao.save(newFaculty);

        assertEquals(currentCount + 1, FacultyDao.count());
    }

    @Test
    void saveAllShouldReturnResultWhenDatabaseHaveFaculties() {
        List<Faculty> newFaculties = Arrays.asList(
                new Faculty(null, "new"),
                new Faculty(null, "new")
        );

        int currentCount = FacultyDao.count();
        FacultyDao.saveAll(newFaculties);

        assertEquals(currentCount + 2, FacultyDao.count());
    }

    @Test
    void deleteByIdShouldReturnResultWhenDatabaseHaveFaculties() {
        int currentCount = FacultyDao.count();
        FacultyDao.deleteById(1);

        assertEquals(currentCount - 1, FacultyDao.count());
    }

    @Test
    void updateShouldReturnResultWhenDatabaseHaveFaculties() {
        Faculty newFaculty = new Faculty(1, "update");
        FacultyDao.update(newFaculty);

        Optional<Faculty> updateFaculty = FacultyDao.findById(1);

        assertTrue(updateFaculty.isPresent());
        assertEquals("update", updateFaculty.get().getName());
    }
}
