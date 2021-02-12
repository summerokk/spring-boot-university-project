package com.att.university.dao;

import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import javax.sql.DataSource;

public abstract class AbstractTest {
    protected void recreateDb(DataSource dataSource) {
        ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator();
        databasePopulator.addScripts(new ClassPathResource("Table.sql"), new ClassPathResource("TestData.sql"));
        DatabasePopulatorUtils.execute(databasePopulator, dataSource);
    }
}
