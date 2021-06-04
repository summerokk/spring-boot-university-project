package com.att.dao;

import com.att.entity.Faculty;
import com.att.entity.Group;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = {"classpath:TestData.sql"})
class GroupRepositoryTest {
    @Autowired
    private GroupRepository groupRepository;

    @Test
    void findAllShouldReturnResultWhenDatabaseHaveGroups() {
        List<Group> expected = Arrays.asList(
                new Group(1, "GT-23", new Faculty(1, "School of Visual arts")),
                new Group(2, "HT-22", new Faculty(2, "Department of Geography")),
                new Group(3, "HY-53", new Faculty(3, "Department of Plant Science"))
        );

        assertThat(groupRepository.findAll()).isEqualTo(expected);
    }

    @Test
    void findByIdShouldReturnResultWhenDatabaseHaveGroups() {
        Group expected = new Group(1, "GT-23", new Faculty(1, "School of Visual arts"));
        Optional<Group> actual = groupRepository.findById(1);

        assertThat(actual).isPresent().hasValue(expected);
    }

    @Test
    void countShouldReturnResultWhenDatabaseHaveGroups() {
        int expected = 3;

        assertThat(groupRepository.count()).isEqualTo(expected);
    }

    @Test
    @Transactional
    void saveShouldReturnResultWhenDatabaseHaveGroups() {
        Group newGroup = new Group(null, "GT-232", new Faculty(1, "School of Visual arts"));
        long currentCount = groupRepository.count();

        groupRepository.save(newGroup);

        assertThat(groupRepository.count()).isEqualTo(currentCount + 1);
    }

    @Test
    @Transactional
    void saveAllShouldReturnResultWhenDatabaseHaveGroups() {
        List<Group> newGroups = Arrays.asList(
                new Group(null, "GT-23", new Faculty(1, "School of Visual arts")),
                new Group(null, "HT-22", new Faculty(2, "Department of Geography"))
        );

        long currentCount = groupRepository.count();
        groupRepository.saveAll(newGroups);

        assertThat(groupRepository.count()).isEqualTo(currentCount + 2);
    }

    @Test
    @Transactional
    void deleteByIdShouldReturnResultWhenDatabaseHaveGroups() {
        long currentCount = groupRepository.count();
        groupRepository.deleteById(3);

        assertThat(groupRepository.count()).isEqualTo(currentCount - 1);
    }

    @Test
    @Transactional
    void updateShouldReturnResultWhenDatabaseHaveGroups() {
        Group newGroup = new Group(1, "GT-24", new Faculty(1, "School of Visual arts"));
        groupRepository.save(newGroup);

        Optional<Group> updateGroup = groupRepository.findById(1);

        assertThat(updateGroup).isPresent();
        assertThat(updateGroup.get().getName()).isEqualTo("GT-24");
    }

    @SpringBootApplication
    @EntityScan("com.att.entity")
    static class TestConfiguration {
    }
}
