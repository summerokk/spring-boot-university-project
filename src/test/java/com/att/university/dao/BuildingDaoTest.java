package com.att.university.dao;

import com.att.university.config.H2Config;
import com.att.university.config.WebTestConfig;
import com.att.university.entity.Building;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {H2Config.class, WebTestConfig.class})
@WebAppConfiguration
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = {"classpath:TestData.sql"})
class BuildingDaoTest {
    @Autowired
    private BuildingDao buildingDao;

    @Test
    void findAllWithParamsShouldReturnResultWhenDatabaseHaveBuildings() {
        List<Building> expected = Arrays.asList(
                new Building(1, "Kirova 32"),
                new Building(2, "Pertova 42"),
                new Building(3, "Pertova 2")
        );

        assertThat(buildingDao.findAll(1, buildingDao.count())).isEqualTo(expected);
    }

    @Test
    void findAllShouldReturnResultWhenDatabaseHaveBuildings() {
        List<Building> expected = Arrays.asList(
                new Building(1, "Kirova 32"),
                new Building(2, "Pertova 42"),
                new Building(3, "Pertova 2")
        );

        assertThat(buildingDao.findAll()).isEqualTo(expected);
    }

    @Test
    void findByIdShouldReturnResultWhenDatabaseHaveBuildings() {
        Building expected = new Building(1, "Kirova 32");
        Optional<Building> building = buildingDao.findById(1);

        assertThat(building).isPresent().hasValue(expected);
    }

    @Test
    void countShouldReturnResultWhenDatabaseHaveBuildings() {
        int expected = 3;

        assertThat(buildingDao.count()).isEqualTo(expected);
    }

    @Test
    @Transactional
    void saveShouldReturnResultWhenDatabaseHaveBuildings() {
        Building building = new Building(null, "new");
        int currentCount = buildingDao.count();

        buildingDao.save(building);

        assertThat(buildingDao.count()).isEqualTo(currentCount + 1);
    }

    @Test
    @Transactional
    void saveAllShouldReturnResultWhenDatabaseHaveBuildings() {
        List<Building> buildings = Arrays.asList(
                new Building(null, "Kirova 32"),
                new Building(null, "Kirova 32"),
                new Building(null, "Kirova 32")
        );

        int currentCount = buildingDao.count();
        buildingDao.saveAll(buildings);

        assertThat(buildingDao.count()).isEqualTo(currentCount + 3);
    }

    @Test
    @Transactional
    void deleteByIdShouldReturnResultWhenDatabaseHaveBuildings() {
        int currentCount = buildingDao.count();
        buildingDao.deleteById(3);

        assertThat(buildingDao.count()).isEqualTo(currentCount - 1);
    }

    @Test
    @Transactional
    void updateShouldReturnResultWhenDatabaseHaveBuildings() {
        Building building = new Building(1, "updateAddress");
        buildingDao.update(building);

        Optional<Building> updateBuilding = buildingDao.findById(1);

        assertThat(updateBuilding).isPresent();
        assertThat(updateBuilding.get().getAddress()).isEqualTo("updateAddress");
    }
}
