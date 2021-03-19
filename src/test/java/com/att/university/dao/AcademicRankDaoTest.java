package com.att.university.dao;

import com.att.university.config.H2Config;
import com.att.university.config.WebTestConfig;
import com.att.university.entity.AcademicRank;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { H2Config.class, WebTestConfig.class})
@WebAppConfiguration
class AcademicRankDaoTest extends AbstractTest {
    @Autowired
    private DataSource dataSource;

    @Autowired
    private AcademicRankDao academicRankDao;

    @BeforeEach
    void tearDown() {
        recreateDb(dataSource);
    }

    @Test
    void findAllShouldReturnResultWhenDatabaseHaveAcademicRanks() {
        List<AcademicRank> expected = Arrays.asList(
                new AcademicRank(1, "Assistant Professor"),
                new AcademicRank(2, "Full Professor"),
                new AcademicRank(3, "Endowed Professor")
        );

        assertThat(academicRankDao.findAll(1, academicRankDao.count())).isEqualTo(expected);
    }

    @Test
    void findByIdShouldReturnResultWhenDatabaseHaveAcademicRanks() {
        AcademicRank expected = new AcademicRank(1, "Assistant Professor");
        Optional<AcademicRank> actual = academicRankDao.findById(1);

        assertThat(actual).isPresent().hasValue(expected);
    }

    @Test
    void countShouldReturnResultWhenDatabaseHaveAcademicRanks() {
        int expected = 3;

        assertThat(academicRankDao.count()).isEqualTo(expected);
    }

    @Test
    void saveShouldReturnResultWhenDatabaseHaveAcademicRanks() {
        AcademicRank newAcademicRank = new AcademicRank(null, "new");
        int currentCount = academicRankDao.count();

        academicRankDao.save(newAcademicRank);

        assertThat(academicRankDao.count()).isEqualTo(currentCount + 1);
    }

    @Test
    void saveAllShouldReturnResultWhenDatabaseHaveAcademicRanks() {
        List<AcademicRank> newAcademicRanks = Arrays.asList(
                new AcademicRank(null, "new"),
                new AcademicRank(null, "new")
        );

        int currentCount = academicRankDao.count();
        academicRankDao.saveAll(newAcademicRanks);

        assertThat(academicRankDao.count()).isEqualTo(currentCount + 2);
    }

    @Test
    void deleteByIdShouldReturnResultWhenDatabaseHaveAcademicRanks() {
        int currentCount = academicRankDao.count();
        academicRankDao.deleteById(1);

        assertThat(academicRankDao.count()).isEqualTo(currentCount - 1);
    }

    @Test
    void updateShouldReturnResultWhenDatabaseHaveAcademicRanks() {
        AcademicRank newAcademicRank = new AcademicRank(1, "update");
        academicRankDao.update(newAcademicRank);

        Optional<AcademicRank> updateAcademicRank = academicRankDao.findById(1);

        assertThat(updateAcademicRank).isPresent();
        assertThat(updateAcademicRank.get().getName()).isEqualTo("update");
    }
}
