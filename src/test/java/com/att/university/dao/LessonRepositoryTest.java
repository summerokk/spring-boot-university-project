package com.att.university.dao;

import com.att.university.entity.AcademicRank;
import com.att.university.entity.Building;
import com.att.university.entity.Classroom;
import com.att.university.entity.Course;
import com.att.university.entity.Faculty;
import com.att.university.entity.Group;
import com.att.university.entity.Lesson;
import com.att.university.entity.ScienceDegree;
import com.att.university.entity.Teacher;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.transaction.Transactional;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@TestPropertySource("/application-test.properties")
@Transactional
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = {"classpath:TestData.sql"})
class LessonRepositoryTest {
    @Autowired
    private LessonRepository lessonRepository;

    @Test
    void findAllShouldReturnResultWhenDatabaseHaveLessons() {
        List<Lesson> expected = getTestLessons();

        assertThat(lessonRepository.findAll()).isEqualTo(expected);
    }

    @Test
    void findByIdShouldReturnResultWhenDatabaseHaveLessons() {
        Lesson expected = getTestLessons().get(0);

        Optional<Lesson> actual = lessonRepository.findById(1);

        assertThat(actual).isPresent().hasValue(expected);
    }

    @Test
    void findByIdShouldReturnEmptyWhenDatabaseHaveNotLessons() {
        Optional<Lesson> actual = lessonRepository.findById(12);

        assertThat(actual).isNotPresent();
    }

    @Test
    void countShouldReturnResultWhenDatabaseHaveLessons() {
        int expected = 3;

        assertThat(lessonRepository.count()).isEqualTo(expected);
    }

    @Test
    void saveAllShouldReturnResultWhenDatabaseHaveLessons() {
        List<Lesson> newLessons = Arrays.asList(
                generateLesson(),
                generateLesson()
        );

        long currentCount = lessonRepository.count();
        lessonRepository.saveAll(newLessons);

        assertThat(lessonRepository.count()).isEqualTo(currentCount + 2);
    }

    @Test
    void deleteByIdShouldReturnResultWhenDatabaseHaveLessons() {
        long currentCount = lessonRepository.count();
        lessonRepository.deleteById(3);

        assertThat(lessonRepository.count()).isEqualTo(currentCount - 1);
    }

    @Test
    void findByDateBetweenShouldReturnScheduleIfLessonsExist() {
        LocalDateTime start = LocalDateTime.parse("2004-10-18T10:23");
        LocalDateTime end = LocalDateTime.parse("2004-11-18T10:23");
        List<Lesson> expected = Arrays.asList(getTestLessons().get(0), getTestLessons().get(1));

        List<Lesson> schedule = lessonRepository.findByDateBetween(start, end);

        assertThat(schedule).isEqualTo(expected);
    }

//    @Test
//    void findTeacherLessonWeeksShouldReturnLocalDateTimesIfLessonsExist() {
//        LocalDateTime start = LocalDateTime.of(2004, 10, 18, 0, 0);
//        LocalDateTime end = LocalDateTime.of(2004, 10, 20, 0, 0);
//
//        List<LocalDateTime> actual = lessonRepository.findTeacherLessonWeeks(start, end, 1);
//
//        List<LocalDateTime> expected = Collections.singletonList(
//                LocalDateTime.of(2004, 10, 18, 0, 0)
//        );
//
//        assertThat(actual).isEqualTo(expected);
//    }

    @Test
    void findTeacherWeekScheduleShouldReturnScheduleIfLessonsExist() {
        LocalDateTime start = LocalDateTime.parse("2004-10-18T10:23");
        LocalDateTime end = start.with(DayOfWeek.SUNDAY);
        List<Lesson> expected = Arrays.asList(getTestLessons().get(0), getTestLessons().get(1));

        List<Lesson> schedule = lessonRepository.findByDateBetweenAndTeacherId(start, end, 1);

        assertThat(schedule).isEqualTo(expected);
    }


    private List<Lesson> getTestLessons() {
        return Arrays.asList(
                Lesson.builder()
                        .withId(1)
                        .withCourse(generateCourse().get(0))
                        .withGroup(generateGroups().get(0))
                        .withTeacher(generateTeacher())
                        .withDate(LocalDateTime.parse("2004-10-19T10:23"))
                        .withClassroom(generateClassroom())
                        .build(),
                Lesson.builder()
                        .withId(2)
                        .withCourse(generateCourse().get(1))
                        .withGroup(generateGroups().get(1))
                        .withTeacher(generateTeacher())
                        .withDate(LocalDateTime.parse("2004-10-20T10:23"))
                        .withClassroom(generateClassroom())
                        .build(),
                Lesson.builder()
                        .withId(3)
                        .withCourse(generateCourse().get(1))
                        .withGroup(generateGroups().get(1))
                        .withTeacher(generateTeacher())
                        .withDate(LocalDateTime.parse("2020-10-20T10:23"))
                        .withClassroom(generateClassroom())
                        .build()
        );
    }

    private Lesson generateLesson() {
        return Lesson.builder()
                .withCourse(generateCourse().get(0))
                .withGroup(generateGroups().get(1))
                .withTeacher(generateTeacher())
                .withDate(LocalDateTime.parse("2004-10-20T10:23"))
                .withClassroom(generateClassroom())
                .build();
    }

    private Teacher generateTeacher() {
        return Teacher.builder()
                .withId(1)
                .withFirstName("Fedor")
                .withLastName("Tolov")
                .withEmail("tolof234@tmail.com")
                .withPassword("password")
                .withAcademicRank(generateAcademicRank())
                .withScienceDegree(generateScienceDegree())
                .withLinkedin("https://link.ru")
                .build();
    }

    private AcademicRank generateAcademicRank() {
        return new AcademicRank(1, "Assistant Professor");
    }

    private ScienceDegree generateScienceDegree() {
        return new ScienceDegree(2, "Doctoral degree");
    }

    private List<Group> generateGroups() {
        return Arrays.asList(
                new Group(1, "GT-23", new Faculty(1, "School of Visual arts")),
                new Group(2, "HT-22", new Faculty(2, "Department of Geography"))
        );
    }

    private List<Course> generateCourse() {
        return Arrays.asList(
                new Course(1, "Special Topics in Agronomy"),
                new Course(2, "Math")
        );
    }

    private Classroom generateClassroom() {
        return new Classroom(1, 12, new Building(1, "Kirova 32"));
    }
}
