package com.att.university.dao;

import com.att.university.entity.Faculty;
import com.att.university.entity.Group;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@TestPropertySource("/application-test.properties")
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = {"classpath:TestData.sql"})
class GroupDaoTest {
    @Autowired
    private GroupDao groupDao;

    @Test
    void findAllShouldReturnResultWhenDatabaseHaveGroups() {
        List<Group> expected = Arrays.asList(
                new Group(1, "GT-23", new Faculty(1, "School of Visual arts")),
                new Group(2, "HT-22", new Faculty(2, "Department of Geography")),
                new Group(3, "HY-53", new Faculty(3, "Department of Plant Science"))
        );

        assertThat(groupDao.findAll(1, groupDao.count())).isEqualTo(expected);
    }

    @Test
    void findByIdShouldReturnResultWhenDatabaseHaveGroups() {
        Group expected = new Group(1, "GT-23", new Faculty(1, "School of Visual arts"));
        Optional<Group> actual = groupDao.findById(1);

        assertThat(actual).isPresent().hasValue(expected);
    }

    @Test
    void countShouldReturnResultWhenDatabaseHaveGroups() {
        int expected = 3;

        assertThat(groupDao.count()).isEqualTo(expected);
    }

    @Test
    @Transactional
    void saveShouldReturnResultWhenDatabaseHaveGroups() {
        Group newGroup = new Group(null, "GT-232", new Faculty(1, "School of Visual arts"));
        int currentCount = groupDao.count();

        groupDao.save(newGroup);

        assertThat(groupDao.count()).isEqualTo(currentCount + 1);
    }

    @Test
    @Transactional
    void saveAllShouldReturnResultWhenDatabaseHaveGroups() {
        List<Group> newGroups = Arrays.asList(
                new Group(null, "GT-23", new Faculty(1, "School of Visual arts")),
                new Group(null, "HT-22", new Faculty(2, "Department of Geography"))
        );

        int currentCount = groupDao.count();
        groupDao.saveAll(newGroups);

        assertThat(groupDao.count()).isEqualTo(currentCount + 2);
    }

    @Test
    @Transactional
    void deleteByIdShouldReturnResultWhenDatabaseHaveGroups() {
        int currentCount = groupDao.count();
        groupDao.deleteById(3);

        assertThat(groupDao.count()).isEqualTo(currentCount - 1);
    }

    @Test
    @Transactional
    void updateShouldReturnResultWhenDatabaseHaveGroups() {
        Group newGroup = new Group(1, "GT-24", new Faculty(1, "School of Visual arts"));
        groupDao.update(newGroup);

        Optional<Group> updateGroup = groupDao.findById(1);

        assertThat(updateGroup).isPresent();
        assertThat(updateGroup.get().getName()).isEqualTo("GT-24");
    }
}
