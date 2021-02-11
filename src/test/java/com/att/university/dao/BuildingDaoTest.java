package com.att.university.dao;

import com.att.university.Config;
import com.att.university.H2Config;
import com.att.university.entity.Building;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {Config.class, H2Config.class})
class BuildingDaoTest {
    @Autowired
    private DataSource dataSource;

    @Autowired
    private BuildingDao buildingDao;

    @BeforeEach
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
        Optional<Building> building = buildingDao.findById(1);

        assertTrue(building.isPresent());
        assertEquals(expected, building.get());
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

        Optional<Building> updateBuilding = buildingDao.findById(1);

        System.out.println(buildingDao.findAll(1, 100));

        assertTrue(updateBuilding.isPresent());
        assertEquals("updateAddress", updateBuilding.get().getAddress());
    }
}
