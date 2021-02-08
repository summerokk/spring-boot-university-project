package com.att.university.dao;

import com.att.university.Config;
import com.att.university.entity.Building;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.sql.DataSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = Config.class)
@ActiveProfiles("test")
class BuildingDaoTest {
    @Autowired
    private DataSource dataSource;

    @Autowired
    private BuildingDao buildingDao;

    @AfterEach
    void tearDown() {
        ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator();
        databasePopulator.addScripts(new ClassPathResource("tables.sql"), new ClassPathResource("startData.sql"));
        DatabasePopulatorUtils.execute(databasePopulator, dataSource);
    }

    @Test
    void findAllShouldReturnResultWhenDatabaseHaveBuildings() {
        List<Building> expected = Arrays.asList(
                new Building(1, "Kirova 32"),
                new Building(2, "Pertova 42")
        );

        assertEquals(expected, buildingDao.findAll(1, buildingDao.count()));
    }

    @Test
    void findByIdShouldReturnResultWhenDatabaseHaveBuildings() {
        Building expected = new Building(1, "Kirova 32");

        assertEquals(expected, buildingDao.findById(1));
    }

    @Test
    void countShouldReturnResultWhenDatabaseHaveBuildings() {
        int expected = 2;

        assertEquals(expected, buildingDao.count());
    }

    @Test
    void saveShouldReturnResultWhenDatabaseHaveBuildings() {
        Building building = new Building(null, "new");
        int currentCount = buildingDao.count();

        buildingDao.save(building);

        assertEquals(currentCount + 1, buildingDao.count());
    }

    @Test
    void saveAllShouldReturnResultWhenDatabaseHaveBuildings() {
        List<Building> buildings = Arrays.asList(
                new Building(null, "Kirova 32"),
                new Building(null, "Kirova 32")
        );

        int currentCount = buildingDao.count();
        buildingDao.saveAll(buildings);

        assertEquals(currentCount + 2, buildingDao.count());
    }

    @Test
    void deleteByIdShouldReturnResultWhenDatabaseHaveBuildings() {
        int currentCount = buildingDao.count();
        buildingDao.deleteById(1);

        assertEquals(currentCount - 1, buildingDao.count());
    }

    @Test
    void updateShouldReturnResultWhenDatabaseHaveBuildings() {
        Building building = new Building(1, "updateAddress");
        buildingDao.update(building);

        Building updateBuilding = buildingDao.findById(1);

        assertEquals("updateAddress", updateBuilding.getAddress());
    }
}
