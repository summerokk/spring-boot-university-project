package com.att.mapper.lesson;

import com.att.entity.AcademicRank;
import com.att.entity.Building;
import com.att.entity.Classroom;
import com.att.entity.Course;
import com.att.entity.Faculty;
import com.att.entity.Group;
import com.att.entity.Lesson;
import com.att.entity.ScienceDegree;
import com.att.entity.Teacher;
import com.att.request.lesson.LessonAddRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class LessonAddRequestMapperTest {
    private final LessonAddRequestMapper requestMapper = Mappers.getMapper(LessonAddRequestMapper.class);

    private Course course;
    private Teacher teacher;
    private Classroom classroom;
    private Group group;

    @BeforeEach
    void init() {
        this.course = generateCourse();
        this.group = generateGroup();
        this.teacher = generateTeacher();
        this.classroom = generateClassroom();
    }

    @Test
    void convertToEntityShouldReturnEntityWhenRequestContainsAllFields() {
        LocalDateTime lessonDate = LocalDateTime.now();

        LessonAddRequest request = LessonAddRequest.builder()
                .withClassroomId(1)
                .withCourseId(1)
                .withTeacherId(1)
                .withDate(lessonDate)
                .build();

        Lesson lesson = requestMapper.convertToEntity(request, course, group, teacher, classroom);

        assertEquals(lessonDate, lesson.getDate());
        assertEquals(course, lesson.getCourse());
    }

    @Test
    void convertToEntityShouldReturnNullWhenRequestIsNull() {
        Lesson lesson = requestMapper.convertToEntity(null, course, group, teacher, classroom);

        assertNull(lesson.getDate());
    }

    @Test
    void convertToEntityShouldReturnNullWhenConvertParametersAreNull() {
        Lesson lesson = requestMapper.convertToEntity(null, null, null, null, null);

        assertNull(lesson);
    }

    @Test
    void convertToEntityShouldReturnNullWhenConvertParametersAreNullExpectRequest() {
        LocalDateTime lessonDate = LocalDateTime.now();

        LessonAddRequest request = LessonAddRequest.builder()
                .withClassroomId(1)
                .withCourseId(1)
                .withTeacherId(1)
                .withDate(lessonDate)
                .build();

        Lesson lesson = requestMapper.convertToEntity(request, null, null, null, null);

        assertEquals(lessonDate, lesson.getDate());
        assertNull(lesson.getCourse());
        assertNull(lesson.getGroup());
        assertNull(lesson.getClassroom());
        assertNull(lesson.getTeacher());
    }

    @Test
    void convertToEntityShouldReturnNullWhenConvertParametersAreNullExceptClassroom() {
        Lesson lesson = requestMapper.convertToEntity(null, null, null, null, classroom);

        assertNull(lesson.getDate());
        assertNull(lesson.getCourse());
        assertNull(lesson.getGroup());
        assertNull(lesson.getTeacher());
        assertEquals(classroom, lesson.getClassroom());
    }

    @Test
    void convertToEntityShouldReturnNullWhenConvertParametersAreNullExceptTeacher() {
        Lesson lesson = requestMapper.convertToEntity(null, null, null, teacher, null);

        assertNull(lesson.getDate());
        assertNull(lesson.getCourse());
        assertNull(lesson.getGroup());
        assertEquals(teacher, lesson.getTeacher());
        assertNull(lesson.getClassroom());
    }

    @Test
    void convertToEntityShouldReturnNullWhenConvertParametersAreNullExceptGroup() {
        Lesson lesson = requestMapper.convertToEntity(null, null, group, null, null);

        assertNull(lesson.getDate());
        assertNull(lesson.getCourse());
        assertEquals(group, lesson.getGroup());
        assertNull(lesson.getTeacher());
        assertNull(lesson.getClassroom());
    }

    private Classroom generateClassroom() {
        Building building = new Building(1, "Address");

        return new Classroom(1, 14, building);
    }

    private Group generateGroup() {
        Faculty faculty = new Faculty(1, "test");

        return new Group(1, "test-23", faculty);
    }

    private Course generateCourse() {
        return new Course(1, "testCourse");
    }

    private Teacher generateTeacher() {
        AcademicRank academicRank = generateRank();
        ScienceDegree scienceDegree = generateDegree();

        return Teacher.builder()
                .withId(1)
                .withFirstName("test")
                .withLastName("test")
                .withEmail("test@test.ru")
                .withPassword("1234567890")
                .withLinkedin("https://test.ru")
                .withAcademicRank(academicRank)
                .withScienceDegree(scienceDegree)
                .build();
    }

    private AcademicRank generateRank() {
        return new AcademicRank(1, "testRank");
    }

    private ScienceDegree generateDegree() {
        return new ScienceDegree(1, "testDegree");
    }
}
