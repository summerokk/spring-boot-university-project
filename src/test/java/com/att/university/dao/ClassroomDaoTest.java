package com.att.university.dao;

import com.att.university.entity.Building;
import com.att.university.entity.Classroom;
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
class ClassroomDaoTest {
    @Autowired
    private ClassroomDao classroomDao;

    @Test
    void findAllShouldReturnResultWhenDatabaseHaveClassrooms() {
        List<Classroom> expected = Arrays.asList(
                new Classroom(1, 12, new Building(1, "Kirova 32")),
                new Classroom(2, 13, new Building(1, "Kirova 32")),
                new Classroom(3, 131, new Building(2, "Pertova 42"))
        );

        assertThat(classroomDao.findAll(1, classroomDao.count())).isEqualTo(expected);
    }

    @Test
    void findByIdShouldReturnResultWhenDatabaseHaveClassrooms() {
        Classroom expected = new Classroom(1, 12, new Building(1, "Kirova 32"));
        Optional<Classroom> actual = classroomDao.findById(1);

        assertThat(actual).isPresent().hasValue(expected);
    }

    @Test
    void countShouldReturnResultWhenDatabaseHaveClassrooms() {
        int expected = 3;

        assertThat(classroomDao.count()).isEqualTo(expected);
    }

    @Test
    @Transactional
    void saveShouldReturnResultWhenDatabaseHaveClassrooms() {
        Classroom newClassroom = new Classroom(null, 233, new Building(1, "Kirova 32"));
        int currentCount = classroomDao.count();

        classroomDao.save(newClassroom);

        assertThat(classroomDao.count()).isEqualTo(currentCount + 1);
    }

    @Test
    @Transactional
    void saveAllShouldReturnResultWhenDatabaseHaveClassrooms() {
        List<Classroom> newClassrooms = Arrays.asList(
                new Classroom(null, 233, new Building(1, "Kirova 32")),
                new Classroom(null, 233, new Building(2, "Pertova 42"))
        );

        int currentCount = classroomDao.count();
        classroomDao.saveAll(newClassrooms);

        assertThat(classroomDao.count()).isEqualTo(currentCount + 2);
    }

    @Test
    @Transactional
    void deleteByIdShouldReturnResultWhenDatabaseHaveClassrooms() {
        int currentCount = classroomDao.count();
        classroomDao.deleteById(3);

        assertThat(classroomDao.count()).isEqualTo(currentCount - 1);
    }

    @Test
    @Transactional
    void updateShouldReturnResultWhenDatabaseHaveClassrooms() {
        Classroom newClassroom = new Classroom(1, 14, new Building(1, "Kirova 32"));
        classroomDao.update(newClassroom);

        Optional<Classroom> updateClassroom = classroomDao.findById(1);

        assertThat(updateClassroom).isPresent();
        assertThat(updateClassroom.get().getNumber()).isEqualTo(14);
    }
}
