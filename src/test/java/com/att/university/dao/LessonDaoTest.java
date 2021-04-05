package com.att.university.dao;

import com.att.university.config.H2Config;
import com.att.university.config.WebTestConfig;
import com.att.university.entity.AcademicRank;
import com.att.university.entity.Building;
import com.att.university.entity.Classroom;
import com.att.university.entity.Course;
import com.att.university.entity.Faculty;
import com.att.university.entity.Group;
import com.att.university.entity.Lesson;
import com.att.university.entity.ScienceDegree;
import com.att.university.entity.Teacher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { H2Config.class, WebTestConfig.class})
@WebAppConfiguration
class LessonDaoTest extends AbstractTest {
    @Autowired
    private DataSource dataSource;

    @Autowired
    private LessonDao lessonDao;

    @BeforeEach
    void tearDown() {
        recreateDb(dataSource);
    }

    @Test
    void findAllShouldReturnResultWhenDatabaseHaveLessons() {
        List<Lesson> expected = getTestLessons();

        assertThat(lessonDao.findAll(1, lessonDao.count())).isEqualTo(expected);
    }

    @Test
    void findByIdShouldReturnResultWhenDatabaseHaveLessons() {
        Lesson expected = getTestLessons().get(0);

        Optional<Lesson> actual = lessonDao.findById(1);

        assertThat(actual).isPresent().hasValue(expected);
    }

    @Test
    void findByIdShouldReturnEmptyWhenDatabaseHaveNotLessons() {
        Optional<Lesson> actual = lessonDao.findById(12);

        assertThat(actual).isNotPresent();
    }

    @Test
    void countShouldReturnResultWhenDatabaseHaveLessons() {
        int expected = 2;

        assertThat(lessonDao.count()).isEqualTo(expected);
    }

    @Test
    void saveShouldReturnResultWhenDatabaseHaveLessons() {
        Lesson newLesson = getTestLessons().get(0);

        int currentCount = lessonDao.count();

        lessonDao.save(newLesson);

        assertThat(lessonDao.count()).isEqualTo(currentCount + 1);
    }

    @Test
    void saveAllShouldReturnResultWhenDatabaseHaveLessons() {
        List<Lesson> newLessons = getTestLessons();

        int currentCount = lessonDao.count();
        lessonDao.saveAll(newLessons);

        assertThat(lessonDao.count()).isEqualTo(currentCount + 2);
    }

    @Test
    void deleteByIdShouldReturnResultWhenDatabaseHaveLessons() {
        int currentCount = lessonDao.count();
        lessonDao.deleteById(1);

        assertThat(lessonDao.count()).isEqualTo(currentCount - 1);
    }

    @Test
    void updateShouldReturnResultWhenDatabaseHaveLessons() {
        Lesson current = getTestLessons().get(0);

        Lesson lesson = current.toBuilder()
                .withDate(LocalDateTime.parse("2004-10-20T10:23"))
                .build();

        lessonDao.update(lesson);

        Optional<Lesson> update = lessonDao.findById(1);

        assertThat(update).isPresent();
        assertThat(update.get().getDate()).isEqualTo(LocalDateTime.parse("2004-10-20T10:23"));
    }

    private List<Lesson> getTestLessons() {
        AcademicRank academicRank = new AcademicRank(1, "Assistant Professor");
        ScienceDegree scienceDegree = new ScienceDegree(2, "Doctoral degree");

        Teacher teacher = Teacher.builder()
                .withId(1)
                .withFirstName("Fedor")
                .withLastName("Tolov")
                .withEmail("tolof234@tmail.com")
                .withPassword("password")
                .withAcademicRank(academicRank)
                .withScienceDegree(scienceDegree)
                .withLinkedin("https://link.ru")
                .build();

        Course course = new Course(1, "Special Topics in Agronomy");
        Course course2 = new Course(2, "Math");

        Group group = new Group(1, "GT-23", new Faculty(1, "School of Visual arts"));
        Group group2 = new Group(2, "HT-22", new Faculty(2, "Department of Geography"));

        Classroom classroom = new Classroom(1, 12, new Building(1, "Kirova 32"));

        return Arrays.asList(
                Lesson.builder()
                        .withId(1)
                        .withCourse(course)
                        .withGroup(group)
                        .withTeacher(teacher)
                        .withDate(LocalDateTime.parse("2004-10-19T10:23"))
                        .withClassroom(classroom)
                        .build(),
                Lesson.builder()
                        .withId(2)
                        .withCourse(course2)
                        .withGroup(group2)
                        .withTeacher(teacher)
                        .withDate(LocalDateTime.parse("2004-10-20T10:23"))
                        .withClassroom(classroom)
                        .build()
        );
    }
}
