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
import com.att.request.lesson.LessonUpdateRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LessonUpdateRequestMapperTest {
    private final LessonUpdateRequestMapper requestMapper = Mappers.getMapper(LessonUpdateRequestMapper.class);

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

        LessonUpdateRequest request = LessonUpdateRequest.builder()
                .withId(1)
                .withClassroomId(1)
                .withCourseId(1)
                .withTeacherId(1)
                .withDate(lessonDate)
                .build();

        Lesson lesson = requestMapper.convertToEntity(request, course, group, teacher, classroom);

        assertEquals(lessonDate, lesson.getDate());
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
