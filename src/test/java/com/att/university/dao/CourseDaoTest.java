package com.att.university.dao;

import com.att.university.Config;
import com.att.university.H2Config;
import com.att.university.entity.Course;
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
class CourseDaoTest {
    @Autowired
    private DataSource dataSource;

    @Autowired
    private CourseDao CourseDao;

    @BeforeEach
    void tearDown() {
        ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator();
        databasePopulator.addScripts(new ClassPathResource("tables.sql"), new ClassPathResource("testData.sql"));
        DatabasePopulatorUtils.execute(databasePopulator, dataSource);
    }

    @Test
    void findAllShouldReturnResultWhenDatabaseHaveCourses() {
        List<Course> expected = Arrays.asList(
                new Course(1, "Special Topics in Agronomy"),
                new Course(2, "Math"),
                new Course(3, "Biology")
        );

        assertEquals(expected, CourseDao.findAll(1, CourseDao.count()));
    }

    @Test
    void findByIdShouldReturnResultWhenDatabaseHaveCourses() {
        Course expected = new Course(1, "Special Topics in Agronomy");
        Optional<Course> actual = CourseDao.findById(1);

        assertTrue(actual.isPresent());
        assertEquals(expected, actual.get());
    }

    @Test
    void countShouldReturnResultWhenDatabaseHaveCourses() {
        int expected = 3;

        assertEquals(expected, CourseDao.count());
    }

    @Test
    void saveShouldReturnResultWhenDatabaseHaveCourses() {
        Course newCourse = new Course(null, "new");
        int currentCount = CourseDao.count();

        CourseDao.save(newCourse);

        assertEquals(currentCount + 1, CourseDao.count());
    }

    @Test
    void saveAllShouldReturnResultWhenDatabaseHaveCourses() {
        List<Course> newCourses = Arrays.asList(
                new Course(null, "new"),
                new Course(null, "new")
        );

        int currentCount = CourseDao.count();
        CourseDao.saveAll(newCourses);

        assertEquals(currentCount + 2, CourseDao.count());
    }

    @Test
    void deleteByIdShouldReturnResultWhenDatabaseHaveCourses() {
        int currentCount = CourseDao.count();
        CourseDao.deleteById(1);

        assertEquals(currentCount - 1, CourseDao.count());
    }

    @Test
    void updateShouldReturnResultWhenDatabaseHaveCourses() {
        Course newCourse = new Course(1, "update");
        CourseDao.update(newCourse);

        Optional<Course> updateCourse = CourseDao.findById(1);

        assertTrue(updateCourse.isPresent());
        assertEquals("update", updateCourse.get().getName());
    }
}
