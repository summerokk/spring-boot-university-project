package com.att.university.dao;

import com.att.university.entity.AcademicRank;
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
class AcademicRankRepositoryTest {
    @Autowired
    private AcademicRankRepository academicRankRepository;

    @Test
    void findAllShouldReturnResultWhenDatabaseHaveAcademicRanks() {
        List<AcademicRank> expected = Arrays.asList(
                new AcademicRank(1, "Assistant Professor"),
                new AcademicRank(2, "Full Professor"),
                new AcademicRank(3, "Endowed Professor")
        );

        assertThat(academicRankRepository.findAll()).isEqualTo(expected);
    }

    @Test
    void findByIdShouldReturnResultWhenDatabaseHaveAcademicRanks() {
        AcademicRank expected = new AcademicRank(1, "Assistant Professor");
        Optional<AcademicRank> actual = academicRankRepository.findById(1);

        assertThat(actual).isPresent().hasValue(expected);
    }

    @Test
    void countShouldReturnResultWhenDatabaseHaveAcademicRanks() {
        int expected = 3;

        assertThat(academicRankRepository.count()).isEqualTo(expected);
    }

    @Test
    @Transactional
    void saveShouldReturnResultWhenDatabaseHaveAcademicRanks() {
        AcademicRank newAcademicRank = new AcademicRank(null, "new");
        long currentCount = academicRankRepository.count();

        academicRankRepository.save(newAcademicRank);

        assertThat(academicRankRepository.count()).isEqualTo(currentCount + 1);
    }

    @Test
    @Transactional
    void saveAllShouldReturnResultWhenDatabaseHaveAcademicRanks() {
        List<AcademicRank> newAcademicRanks = Arrays.asList(
                new AcademicRank(null, "new"),
                new AcademicRank(null, "new")
        );

        long currentCount = academicRankRepository.count();
        academicRankRepository.saveAll(newAcademicRanks);

        assertThat(academicRankRepository.count()).isEqualTo(currentCount + 2);
    }

    @Test
    @Transactional
    void deleteByIdShouldReturnResultWhenDatabaseHaveAcademicRanks() {
        long currentCount = academicRankRepository.count();
        academicRankRepository.deleteById(3);

        assertThat(academicRankRepository.count()).isEqualTo(currentCount - 1);
    }

    @Test
    @Transactional
    void updateShouldReturnResultWhenDatabaseHaveAcademicRanks() {
        AcademicRank newAcademicRank = new AcademicRank(1, "update");
        academicRankRepository.save(newAcademicRank);

        Optional<AcademicRank> updateAcademicRank = academicRankRepository.findById(1);

        assertThat(updateAcademicRank).isPresent();
        assertThat(updateAcademicRank.get().getName()).isEqualTo("update");
    }
}
