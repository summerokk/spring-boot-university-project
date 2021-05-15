package com.att.university.dao;

import com.att.university.config.H2Config;
import com.att.university.config.WebTestConfig;
import com.att.university.entity.ScienceDegree;
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
class ScienceDegreeDaoTest {
    @Autowired
    private ScienceDegreeDao scienceDegreeDao;

    @Test
    void findAllShouldReturnResultWhenDatabaseHaveScienceDegrees() {
        List<ScienceDegree> expected = Arrays.asList(
                new ScienceDegree(1, "Associate degree"),
                new ScienceDegree(2, "Doctoral degree"),
                new ScienceDegree(3, "Bachelor's degree"),
                new ScienceDegree(4, "Master's degree")
        );

        assertThat(scienceDegreeDao.findAll(1, scienceDegreeDao.count())).isEqualTo(expected);
    }

    @Test
    void findByIdShouldReturnResultWhenDatabaseHaveScienceDegrees() {
        ScienceDegree expected = new ScienceDegree(1, "Associate degree");
        Optional<ScienceDegree> actual = scienceDegreeDao.findById(1);

        assertThat(actual).isPresent().hasValue(expected);
    }

    @Test
    void countShouldReturnResultWhenDatabaseHaveScienceDegrees() {
        int expected = 4;

        assertThat(scienceDegreeDao.count()).isEqualTo(expected);
    }

    @Test
    void saveShouldReturnResultWhenDatabaseHaveScienceDegrees() {
        ScienceDegree newScienceDegree = new ScienceDegree(null, "new");
        int currentCount = scienceDegreeDao.count();

        scienceDegreeDao.save(newScienceDegree);

        assertThat(scienceDegreeDao.count()).isEqualTo(currentCount + 1);
    }

    @Test
    void saveAllShouldReturnResultWhenDatabaseHaveScienceDegrees() {
        List<ScienceDegree> newScienceDegrees = Arrays.asList(
                new ScienceDegree(null, "new"),
                new ScienceDegree(null, "new")
        );

        int currentCount = scienceDegreeDao.count();
        scienceDegreeDao.saveAll(newScienceDegrees);

        assertThat(scienceDegreeDao.count()).isEqualTo(currentCount + 2);
    }

    @Test
    void deleteByIdShouldReturnResultWhenDatabaseHaveScienceDegrees() {
        int currentCount = scienceDegreeDao.count();
        scienceDegreeDao.deleteById(3);

        assertThat(scienceDegreeDao.count()).isEqualTo(currentCount - 1);
    }

    @Test
    void updateShouldReturnResultWhenDatabaseHaveScienceDegrees() {
        ScienceDegree newScienceDegree = new ScienceDegree(1, "update");
        scienceDegreeDao.update(newScienceDegree);

        Optional<ScienceDegree> updateScienceDegree = scienceDegreeDao.findById(1);

        assertThat(updateScienceDegree).isPresent();
        assertThat(updateScienceDegree.get().getName()).isEqualTo("update");
    }
}
