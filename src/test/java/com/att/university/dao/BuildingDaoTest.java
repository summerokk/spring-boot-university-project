package com.att.university.dao;

import com.att.university.Config;
import com.att.university.dao.impl.BuildingDaoImpl;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import javax.sql.DataSource;

import static org.junit.jupiter.api.Assertions.*;
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = Config.class)
class BuildingDaoTest {
    @Autowired
    private DataSource dataSource;


    @Test
    public void test() {
        System.out.println(dataSource);
    }
}