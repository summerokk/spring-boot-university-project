package com.att.university.dao;

import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import javax.sql.DataSource;

public abstract class AbstractTest {
    private static final String TABLE_FILE_NAME = "Table.sql";
    private static final String TEST_DATA_FILE_NAME = "TestData.sql";

    protected void recreateDb(DataSource dataSource) {
        ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator();
        databasePopulator.addScripts(new ClassPathResource(TABLE_FILE_NAME), new ClassPathResource(TEST_DATA_FILE_NAME));
        DatabasePopulatorUtils.execute(databasePopulator, dataSource);
    }
}
