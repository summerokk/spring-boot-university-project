package com.att.university.dao;

import com.att.university.Config;
import com.att.university.H2Config;
import com.att.university.entity.ScienceDegree;
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
class ScienceDegreeDaoTest {
    @Autowired
    private DataSource dataSource;

    @Autowired
    private ScienceDegreeDao ScienceDegreeDao;

    @BeforeEach
    void tearDown() {
        ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator();
        databasePopulator.addScripts(new ClassPathResource("tables.sql"), new ClassPathResource("startData.sql"));
        DatabasePopulatorUtils.execute(databasePopulator, dataSource);
    }

    @Test
    void findAllShouldReturnResultWhenDatabaseHaveScienceDegrees() {
        List<ScienceDegree> expected = Arrays.asList(
                new ScienceDegree(1, "Associate degree"),
                new ScienceDegree(2, "Doctoral degree"),
                new ScienceDegree(3, "Bachelor's degree"),
                new ScienceDegree(4, "Master's degree")
        );

        assertEquals(expected, ScienceDegreeDao.findAll(1, ScienceDegreeDao.count()));
    }

    @Test
    void findByIdShouldReturnResultWhenDatabaseHaveScienceDegrees() {
        ScienceDegree expected = new ScienceDegree(1, "Associate degree");
        Optional<ScienceDegree> actual = ScienceDegreeDao.findById(1);

        assertTrue(actual.isPresent());
        assertEquals(expected, actual.get());
    }

    @Test
    void countShouldReturnResultWhenDatabaseHaveScienceDegrees() {
        int expected = 4;

        assertEquals(expected, ScienceDegreeDao.count());
    }

    @Test
    void saveShouldReturnResultWhenDatabaseHaveScienceDegrees() {
        ScienceDegree newScienceDegree = new ScienceDegree(null, "new");
        int currentCount = ScienceDegreeDao.count();

        ScienceDegreeDao.save(newScienceDegree);

        assertEquals(currentCount + 1, ScienceDegreeDao.count());
    }

    @Test
    void saveAllShouldReturnResultWhenDatabaseHaveScienceDegrees() {
        List<ScienceDegree> newScienceDegrees = Arrays.asList(
                new ScienceDegree(null, "new"),
                new ScienceDegree(null, "new")
        );

        int currentCount = ScienceDegreeDao.count();
        ScienceDegreeDao.saveAll(newScienceDegrees);

        assertEquals(currentCount + 2, ScienceDegreeDao.count());
    }

    @Test
    void deleteByIdShouldReturnResultWhenDatabaseHaveScienceDegrees() {
        int currentCount = ScienceDegreeDao.count();
        ScienceDegreeDao.deleteById(1);

        assertEquals(currentCount - 1, ScienceDegreeDao.count());
    }

    @Test
    void updateShouldReturnResultWhenDatabaseHaveScienceDegrees() {
        ScienceDegree newScienceDegree = new ScienceDegree(1, "update");
        ScienceDegreeDao.update(newScienceDegree);

        Optional<ScienceDegree> updateScienceDegree = ScienceDegreeDao.findById(1);

        assertTrue(updateScienceDegree.isPresent());
        assertEquals("update", updateScienceDegree.get().getName());
    }
}
