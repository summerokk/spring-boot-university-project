package com.att.university.dao;

import com.att.university.Config;
import com.att.university.H2Config;
import com.att.university.entity.Building;
import com.att.university.entity.Classroom;
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
class ClassroomDaoTest extends AbstractTest {
    @Autowired
    private DataSource dataSource;

    @Autowired
    private ClassroomDao classroomDao;

    @BeforeEach
    void tearDown() {
        recreateDb(dataSource);
    }

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
    void saveShouldReturnResultWhenDatabaseHaveClassrooms() {
        Classroom newClassroom = new Classroom(null, 233, new Building(1, "Kirova 32"));
        int currentCount = classroomDao.count();

        classroomDao.save(newClassroom);

        assertThat(classroomDao.count()).isEqualTo(currentCount + 1);
    }

    @Test
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
    void deleteByIdShouldReturnResultWhenDatabaseHaveClassrooms() {
        int currentCount = classroomDao.count();
        classroomDao.deleteById(1);

        assertThat(classroomDao.count()).isEqualTo(currentCount - 1);
    }

    @Test
    void updateShouldReturnResultWhenDatabaseHaveClassrooms() {
        Classroom newClassroom = new Classroom(1, 14, new Building(1, "Kirova 32"));
        classroomDao.update(newClassroom);

        Optional<Classroom> updateClassroom = classroomDao.findById(1);

        assertThat(updateClassroom).isPresent();
        assertThat(updateClassroom.get().getNumber()).isEqualTo(14);
    }
}
