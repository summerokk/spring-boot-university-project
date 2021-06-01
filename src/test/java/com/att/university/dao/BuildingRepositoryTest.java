package com.att.university.dao;

import com.att.university.entity.Building;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@TestPropertySource("/application-test.properties")
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = {"classpath:TestData.sql"})
class BuildingRepositoryTest {
    @Autowired
    private BuildingRepository buildingRepository;

    @Test
    void findAllWithParamsShouldReturnResultWhenDatabaseHaveBuildings() {
        List<Building> expected = Arrays.asList(
                new Building(1, "Kirova 32"),
                new Building(2, "Pertova 42"),
                new Building(3, "Pertova 2")
        );

        assertThat(buildingRepository.findAll()).isEqualTo(expected);
    }

    @Test
    void findAllShouldReturnResultWhenDatabaseHaveBuildings() {
        List<Building> expected = Arrays.asList(
                new Building(1, "Kirova 32"),
                new Building(2, "Pertova 42"),
                new Building(3, "Pertova 2")
        );

        assertThat(buildingRepository.findAll()).isEqualTo(expected);
    }

    @Test
    void findByIdShouldReturnResultWhenDatabaseHaveBuildings() {
        Building expected = new Building(1, "Kirova 32");
        Optional<Building> building = buildingRepository.findById(1);

        assertThat(building).isPresent().hasValue(expected);
    }

    @Test
    void countShouldReturnResultWhenDatabaseHaveBuildings() {
        int expected = 3;

        assertThat(buildingRepository.count()).isEqualTo(expected);
    }

    @Test
    @Transactional
    void saveShouldReturnResultWhenDatabaseHaveBuildings() {
        Building building = new Building(null, "new");
        long currentCount = buildingRepository.count();

        buildingRepository.save(building);

        assertThat(buildingRepository.count()).isEqualTo(currentCount + 1);
    }

    @Test
    @Transactional
    void saveAllShouldReturnResultWhenDatabaseHaveBuildings() {
        List<Building> buildings = Arrays.asList(
                new Building(null, "Kirova 32"),
                new Building(null, "Kirova 32"),
                new Building(null, "Kirova 32")
        );

        long currentCount = buildingRepository.count();
        buildingRepository.saveAll(buildings);

        assertThat(buildingRepository.count()).isEqualTo(currentCount + 3);
    }

    @Test
    @Transactional
    void deleteByIdShouldReturnResultWhenDatabaseHaveBuildings() {
        long currentCount = buildingRepository.count();
        buildingRepository.deleteById(3);

        assertThat(buildingRepository.count()).isEqualTo(currentCount - 1);
    }

    @Test
    @Transactional
    void updateShouldReturnResultWhenDatabaseHaveBuildings() {
        Building building = new Building(1, "updateAddress");
        buildingRepository.save(building);

        Optional<Building> updateBuilding = buildingRepository.findById(1);

        assertThat(updateBuilding).isPresent();
        assertThat(updateBuilding.get().getAddress()).isEqualTo("updateAddress");
    }
}
