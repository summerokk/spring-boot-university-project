package com.att.university.dao;

import com.att.university.config.H2Config;
import com.att.university.config.WebTestConfig;
import com.att.university.entity.Course;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {H2Config.class, WebTestConfig.class})
@WebAppConfiguration
@Transactional
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = {"classpath:TestData.sql"})
class CourseDaoTest {
    @Autowired
    private CourseDao courseDao;

    @Test
    void findAllShouldReturnResultWhenDatabaseHaveCourses() {
        List<Course> expected = Arrays.asList(
                new Course(1, "Special Topics in Agronomy"),
                new Course(2, "Math"),
                new Course(3, "Biology")
        );

        assertThat(courseDao.findAll(1, courseDao.count())).isEqualTo(expected);
    }

    @Test
    void findByIdShouldReturnResultWhenDatabaseHaveCourses() {
        Course expected = new Course(1, "Special Topics in Agronomy");
        Optional<Course> actual = courseDao.findById(1);

        assertThat(actual).isPresent().hasValue(expected);
    }

    @Test
    void countShouldReturnResultWhenDatabaseHaveCourses() {
        int expected = 3;

        assertThat(courseDao.count()).isEqualTo(expected);
    }

    @Test
    void saveShouldReturnResultWhenDatabaseHaveCourses() {
        Course newCourse = new Course(null, "new");
        int currentCount = courseDao.count();

        courseDao.save(newCourse);

        assertThat(courseDao.count()).isEqualTo(currentCount + 1);
    }

    @Test
    void saveAllShouldReturnResultWhenDatabaseHaveCourses() {
        List<Course> newCourses = Arrays.asList(
                new Course(null, "new"),
                new Course(null, "new")
        );

        int currentCount = courseDao.count();
        courseDao.saveAll(newCourses);

        assertThat(courseDao.count()).isEqualTo(currentCount + 2);
    }

    @Test
    void deleteByIdShouldReturnResultWhenDatabaseHaveCourses() {
        int currentCount = courseDao.count();
        courseDao.deleteById(3);

        assertThat(courseDao.count()).isEqualTo(currentCount - 1);
    }

    @Test
    void updateShouldReturnResultWhenDatabaseHaveCourses() {
        Course newCourse = new Course(1, "update");
        courseDao.update(newCourse);

        Optional<Course> updateCourse = courseDao.findById(1);

        assertThat(updateCourse).isPresent();
        assertThat(updateCourse.get().getName()).isEqualTo("update");
    }
}
