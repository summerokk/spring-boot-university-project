package com.att.university;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import javax.sql.DataSource;
import java.util.Scanner;

@Configuration
@ComponentScan(excludeFilters = {@ComponentScan.Filter(
        type = FilterType.ASSIGNABLE_TYPE, classes = {H2Config.class})})
@PropertySource("classpath:app.properties")
public class Config {
    private final Environment env;

    @Autowired
    public Config(Environment environment) {
        this.env = environment;
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource source) {
        return new JdbcTemplate(source);
    }

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(env.getProperty("connection.driver"));
        dataSource.setUrl(env.getProperty("connection.url"));
        dataSource.setUsername(env.getProperty("connection.username"));
        dataSource.setPassword(env.getProperty("connection.password"));

        ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator();
        databasePopulator.addScripts(new ClassPathResource("tables.sql"), new ClassPathResource("startData.sql"));
        DatabasePopulatorUtils.execute(databasePopulator, dataSource);

        return dataSource;
    }

    @Bean
    public Scanner scanner() {
        return new Scanner(System.in);
    }
}
