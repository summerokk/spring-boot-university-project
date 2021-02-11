package com.att.university;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;

@Configuration
public class H2Config {
    @Bean
    @Primary
    public DataSource dataSourceTest() {
        return new EmbeddedDatabaseBuilder()
                .setName("university_test")
                .setType(EmbeddedDatabaseType.H2)
                .setScriptEncoding("UTF-8")
                .ignoreFailedDrops(true)
                .addScripts("tables.sql", "StartData.sql")
                .build();
    }
}
