package com.att.university.dao;

import com.att.university.Config;
import com.att.university.H2Config;
import com.att.university.entity.Building;
import com.att.university.entity.Classroom;
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
class ClassroomDaoTest {
    @Autowired
    private DataSource dataSource;

    @Autowired
    private ClassroomDao classroomDao;

    @BeforeEach
    void tearDown() {
        ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator();
        databasePopulator.addScripts(new ClassPathResource("Table.sql"), new ClassPathResource("testData.sql"));
        DatabasePopulatorUtils.execute(databasePopulator, dataSource);
    }

    @Test
    void findAllShouldReturnResultWhenDatabaseHaveClassrooms() {
        List<Classroom> expected = Arrays.asList(
                new Classroom(1, 12, new Building(1, "Kirova 32")),
                new Classroom(2, 13, new Building(1, "Kirova 32")),
                new Classroom(3, 131, new Building(2, "Pertova 42"))
        );

        assertEquals(expected, classroomDao.findAll(1, classroomDao.count()));
    }

    @Test
    void findByIdShouldReturnResultWhenDatabaseHaveClassrooms() {
        Classroom expected = new Classroom(1, 12, new Building(1, "Kirova 32"));
        Optional<Classroom> actual = classroomDao.findById(1);

        assertTrue(actual.isPresent());
        assertEquals(expected, actual.get());
    }

    @Test
    void countShouldReturnResultWhenDatabaseHaveClassrooms() {
        int expected = 3;

        assertEquals(expected, classroomDao.count());
    }

    @Test
    void saveShouldReturnResultWhenDatabaseHaveClassrooms() {
        Classroom newClassroom = new Classroom(null, 233, new Building(1, "Kirova 32"));
        int currentCount = classroomDao.count();

        classroomDao.save(newClassroom);

        assertEquals(currentCount + 1, classroomDao.count());
    }

    @Test
    void saveAllShouldReturnResultWhenDatabaseHaveClassrooms() {
        List<Classroom> newClassrooms = Arrays.asList(
                new Classroom(null, 233, new Building(1, "Kirova 32")),
                new Classroom(null, 233, new Building(2, "Pertova 42"))
        );

        int currentCount = classroomDao.count();
        classroomDao.saveAll(newClassrooms);

        assertEquals(currentCount + 2, classroomDao.count());
    }

    @Test
    void deleteByIdShouldReturnResultWhenDatabaseHaveClassrooms() {
        int currentCount = classroomDao.count();
        classroomDao.deleteById(1);

        assertEquals(currentCount - 1, classroomDao.count());
    }

    @Test
    void updateShouldReturnResultWhenDatabaseHaveClassrooms() {
        Classroom newClassroom = new Classroom(1, 14, new Building(1, "Kirova 32"));
        classroomDao.update(newClassroom);

        Optional<Classroom> updateClassroom = classroomDao.findById(1);

        assertTrue(updateClassroom.isPresent());
        assertEquals(14, updateClassroom.get().getNumber());
    }
}
