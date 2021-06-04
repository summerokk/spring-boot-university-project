package com.att.dao;

import com.att.entity.Course;
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
class CourseRepositoryTest {
    @Autowired
    private CourseRepository courseRepository;

    @Test
    void findAllShouldReturnResultWhenDatabaseHaveCourses() {
        List<Course> expected = Arrays.asList(
                new Course(1, "Special Topics in Agronomy"),
                new Course(2, "Math"),
                new Course(3, "Biology")
        );

        assertThat(courseRepository.findAll()).isEqualTo(expected);
    }

    @Test
    void findByIdShouldReturnResultWhenDatabaseHaveCourses() {
        Course expected = new Course(1, "Special Topics in Agronomy");
        Optional<Course> actual = courseRepository.findById(1);

        assertThat(actual).isPresent().hasValue(expected);
    }

    @Test
    void countShouldReturnResultWhenDatabaseHaveCourses() {
        int expected = 3;

        assertThat(courseRepository.count()).isEqualTo(expected);
    }

    @Test
    void saveShouldReturnResultWhenDatabaseHaveCourses() {
        Course newCourse = new Course(null, "new");
        long currentCount = courseRepository.count();

        courseRepository.save(newCourse);

        assertThat(courseRepository.count()).isEqualTo(currentCount + 1);
    }

    @Test
    void saveAllShouldReturnResultWhenDatabaseHaveCourses() {
        List<Course> newCourses = Arrays.asList(
                new Course(null, "new"),
                new Course(null, "new")
        );

        long currentCount = courseRepository.count();
        courseRepository.saveAll(newCourses);

        assertThat(courseRepository.count()).isEqualTo(currentCount + 2);
    }

    @Test
    void deleteByIdShouldReturnResultWhenDatabaseHaveCourses() {
        long currentCount = courseRepository.count();
        courseRepository.deleteById(3);

        assertThat(courseRepository.count()).isEqualTo(currentCount - 1);
    }

    @Test
    void updateShouldReturnResultWhenDatabaseHaveCourses() {
        Course newCourse = new Course(1, "update");
        courseRepository.save(newCourse);

        Optional<Course> updateCourse = courseRepository.findById(1);

        assertThat(updateCourse).isPresent();
        assertThat(updateCourse.get().getName()).isEqualTo("update");
    }

    @SpringBootApplication
    @EntityScan("com.att.entity")
    static class TestConfiguration {
    }
}
