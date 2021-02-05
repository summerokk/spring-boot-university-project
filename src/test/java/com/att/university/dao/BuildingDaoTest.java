package com.att.university.dao;

import com.att.university.Config;
import com.att.university.entity.Building;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ContextConfiguration(classes = Config.class)
@ActiveProfiles("test")
class BuildingDaoTest {
    @Autowired
    private BuildingDao buildingDao;

    @Test
    void findAllShouldReturnResultWhenDatabaseHaveBuildings() {
        List<Building> expected = Arrays.asList(
             new Building(1, "Kirova 32"),
             new Building(2, "Pertova 42")
        );

        assertEquals(expected, buildingDao.findAll());
    }
}
