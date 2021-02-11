package com.att.university.dao;

import com.att.university.Config;
import com.att.university.H2Config;
import com.att.university.entity.AcademicRank;
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
class AcademicRankDaoTest {
    @Autowired
    private DataSource dataSource;

    @Autowired
    private AcademicRankDao academicRankDao;

    @BeforeEach
    void tearDown() {
        ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator();
        databasePopulator.addScripts(new ClassPathResource("tables.sql"), new ClassPathResource("startData.sql"));
        DatabasePopulatorUtils.execute(databasePopulator, dataSource);
    }

    @Test
    void findAllShouldReturnResultWhenDatabaseHaveAcademicRanks() {
        List<AcademicRank> expected = Arrays.asList(
                new AcademicRank(1, "Assistant Professor"),
                new AcademicRank(2, "Full Professor"),
                new AcademicRank(3, "Endowed Professor")
        );

        assertEquals(expected, academicRankDao.findAll(1, academicRankDao.count()));
    }

    @Test
    void findByIdShouldReturnResultWhenDatabaseHaveAcademicRanks() {
        AcademicRank expected = new AcademicRank(1, "Assistant Professor");
        Optional<AcademicRank> actual = academicRankDao.findById(1);

        assertTrue(actual.isPresent());
        assertEquals(expected, actual.get());
    }

    @Test
    void countShouldReturnResultWhenDatabaseHaveAcademicRanks() {
        int expected = 3;

        assertEquals(expected, academicRankDao.count());
    }

    @Test
    void saveShouldReturnResultWhenDatabaseHaveAcademicRanks() {
        AcademicRank newAcademicRank = new AcademicRank(null, "new");
        int currentCount = academicRankDao.count();

        academicRankDao.save(newAcademicRank);

        assertEquals(currentCount + 1, academicRankDao.count());
    }

    @Test
    void saveAllShouldReturnResultWhenDatabaseHaveAcademicRanks() {
        List<AcademicRank> newAcademicRanks = Arrays.asList(
                new AcademicRank(null, "new"),
                new AcademicRank(null, "new")
        );

        int currentCount = academicRankDao.count();
        academicRankDao.saveAll(newAcademicRanks);

        assertEquals(currentCount + 2, academicRankDao.count());
    }

    @Test
    void deleteByIdShouldReturnResultWhenDatabaseHaveAcademicRanks() {
        int currentCount = academicRankDao.count();
        academicRankDao.deleteById(1);

        assertEquals(currentCount - 1, academicRankDao.count());
    }

    @Test
    void updateShouldReturnResultWhenDatabaseHaveAcademicRanks() {
        AcademicRank newAcademicRank = new AcademicRank(1, "update");
        academicRankDao.update(newAcademicRank);

        Optional<AcademicRank> updateAcademicRank = academicRankDao.findById(1);

        assertTrue(updateAcademicRank.isPresent());
        assertEquals("update", updateAcademicRank.get().getName());
    }
}
