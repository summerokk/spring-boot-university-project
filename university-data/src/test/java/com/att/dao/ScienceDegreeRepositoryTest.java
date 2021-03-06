package com.att.dao;

import com.att.entity.ScienceDegree;
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
class ScienceDegreeRepositoryTest {
    @Autowired
    private ScienceDegreeRepository scienceDegreeRepository;

    @Test
    void findAllShouldReturnResultWhenDatabaseHaveScienceDegrees() {
        List<ScienceDegree> expected = Arrays.asList(
                new ScienceDegree(1, "Associate degree"),
                new ScienceDegree(2, "Doctoral degree"),
                new ScienceDegree(3, "Bachelor's degree"),
                new ScienceDegree(4, "Master's degree")
        );

        assertThat(scienceDegreeRepository.findAll()).isEqualTo(expected);
    }

    @Test
    void findByIdShouldReturnResultWhenDatabaseHaveScienceDegrees() {
        ScienceDegree expected = new ScienceDegree(1, "Associate degree");
        Optional<ScienceDegree> actual = scienceDegreeRepository.findById(1);

        assertThat(actual).isPresent().hasValue(expected);
    }

    @Test
    void countShouldReturnResultWhenDatabaseHaveScienceDegrees() {
        int expected = 4;

        assertThat(scienceDegreeRepository.count()).isEqualTo(expected);
    }

    @Test
    void saveShouldReturnResultWhenDatabaseHaveScienceDegrees() {
        ScienceDegree newScienceDegree = new ScienceDegree(null, "new");
        long currentCount = scienceDegreeRepository.count();

        scienceDegreeRepository.save(newScienceDegree);

        assertThat(scienceDegreeRepository.count()).isEqualTo(currentCount + 1);
    }

    @Test
    void saveAllShouldReturnResultWhenDatabaseHaveScienceDegrees() {
        List<ScienceDegree> newScienceDegrees = Arrays.asList(
                new ScienceDegree(null, "new"),
                new ScienceDegree(null, "new")
        );

        long currentCount = scienceDegreeRepository.count();
        scienceDegreeRepository.saveAll(newScienceDegrees);

        assertThat(scienceDegreeRepository.count()).isEqualTo(currentCount + 2);
    }

    @Test
    void deleteByIdShouldReturnResultWhenDatabaseHaveScienceDegrees() {
        long currentCount = scienceDegreeRepository.count();
        scienceDegreeRepository.deleteById(3);

        assertThat(scienceDegreeRepository.count()).isEqualTo(currentCount - 1);
    }

    @Test
    void updateShouldReturnResultWhenDatabaseHaveScienceDegrees() {
        ScienceDegree newScienceDegree = new ScienceDegree(1, "update");
        scienceDegreeRepository.save(newScienceDegree);

        Optional<ScienceDegree> updateScienceDegree = scienceDegreeRepository.findById(1);

        assertThat(updateScienceDegree).isPresent();
        assertThat(updateScienceDegree.get().getName()).isEqualTo("update");
    }

    @SpringBootApplication
    @EntityScan("com.att.entity")
    static class TestConfiguration {
    }
}
