package com.att.university;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;

@Configuration
@ComponentScan
@PropertySource("classpath:h2.properties")
public class H2Config {
    @Bean
    public JdbcTemplate jdbcTemplate(@Qualifier("dataSourceTest") DataSource source) {
        return new JdbcTemplate(source);
    }

    @Bean
    public DataSource dataSourceTest(@Value("${h2.name}") String name) {
        return new EmbeddedDatabaseBuilder()
                .setName(name)
                .setType(EmbeddedDatabaseType.H2)
                .setScriptEncoding("UTF-8")
                .ignoreFailedDrops(true)
                .addScripts("tables.sql", "TestData.sql")
                .build();
    }
}
