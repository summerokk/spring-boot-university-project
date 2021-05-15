package com.att.university.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@PropertySource("classpath:application.properties")
@EnableJpaRepositories
public class H2Config {
    @Bean
    public JdbcTemplate jdbcTemplate(@Qualifier("dataSourceTest") DataSource source) {
        return new JdbcTemplate(source);
    }

    @Bean
    public DataSource dataSourceTest(@Value("${connection.url}") String url,
                                     @Value("${connection.username}") String username,
                                     @Value("${connection.password}") String password) {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(url);
        hikariConfig.setUsername(username);
        hikariConfig.setPassword(password);

        return new HikariDataSource(hikariConfig);
    }

    @Bean(name = "entityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManager(DataSource dataSource,
                                                                @Value("${hibernate.hbm2ddl}") String hbm2ddl,
                                                                @Value("${hibernate.dialect}") String dialect,
                                                                @Value("${hibernate.batch_size}") String batchSize) {
        LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactory.setDataSource(dataSource);
        entityManagerFactory.setPackagesToScan("com.att.university.entity");

        Properties properties = new Properties();
        properties.setProperty("hibernate.hbm2ddl.auto", hbm2ddl);
        properties.setProperty("hibernate.dialect", dialect);
        properties.setProperty("hibernate.jdbc.batch_size", batchSize);
        properties.setProperty("hibernate.show_sql", "true");


        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        entityManagerFactory.setJpaVendorAdapter(vendorAdapter);
        entityManagerFactory.setJpaProperties(properties);

        return entityManagerFactory;
    }

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory);

        return transactionManager;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }
}
