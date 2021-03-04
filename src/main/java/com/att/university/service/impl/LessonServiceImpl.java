package com.att.university.service.impl;

import com.att.university.dao.ClassroomDao;
import com.att.university.dao.CourseDao;
import com.att.university.dao.GroupDao;
import com.att.university.dao.LessonDao;
import com.att.university.dao.TeacherDao;
import com.att.university.entity.Classroom;
import com.att.university.entity.Course;
import com.att.university.entity.Group;
import com.att.university.entity.Lesson;
import com.att.university.entity.Teacher;
import com.att.university.exception.dao.LessonException;
import com.att.university.request.lesson.LessonAddRequest;
import com.att.university.request.lesson.LessonUpdateRequest;
import com.att.university.service.LessonService;
import com.att.university.validator.lesson.LessonAddValidator;
import com.att.university.validator.lesson.LessonUpdateValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class LessonServiceImpl implements LessonService {
    private final LessonAddValidator lessonAddValidator;
    private final LessonUpdateValidator lessonUpdateValidator;
    private final TeacherDao teacherDao;
    private final LessonDao lessonDao;
    private final CourseDao courseDao;
    private final GroupDao groupDao;
    private final ClassroomDao classroomDao;

    @Override
    @Transactional
    public void add(LessonAddRequest addRequest) {
        lessonAddValidator.validate(addRequest);

        Course course = courseDao.findById(addRequest.getCourseId())
                .orElseThrow(() -> new LessonException("Course is not found"));

        Classroom classroom = classroomDao.findById(addRequest.getClassroomId())
                .orElseThrow(() -> new LessonException("Classroom is not found"));

        Group group = groupDao.findById(addRequest.getGroupId())
                .orElseThrow(() -> new LessonException("Group is not found"));

        Teacher teacher = teacherDao.findById(addRequest.getTeacherId())
                .orElseThrow(() -> new LessonException("Teacher is not found"));

        LocalDateTime date = LocalDateTime.parse(addRequest.getDate());

        lessonDao.save(Lesson.builder()
                .withTeacher(teacher)
                .withGroup(group)
                .withDate(date)
                .withClassroom(classroom)
                .withCourse(course)
                .build());
    }

    @Override
    @Transactional
    public void update(LessonUpdateRequest updateRequest) {
        lessonUpdateValidator.validate(updateRequest);

        Lesson lesson = lessonDao.findById(updateRequest.getId())
                .orElseThrow(() -> new LessonException("Lesson is not found"));

        Course course = courseDao.findById(updateRequest.getCourseId())
                .orElseThrow(() -> new LessonException("Course is not found"));

        Classroom classroom = classroomDao.findById(updateRequest.getClassroomId())
                .orElseThrow(() -> new LessonException("Classroom is not found"));

        Group group = groupDao.findById(updateRequest.getGroupId())
                .orElseThrow(() -> new LessonException("Group is not found"));

        Teacher teacher = teacherDao.findById(updateRequest.getTeacherId())
                .orElseThrow(() -> new LessonException("Teacher is not found"));

        LocalDateTime date = LocalDateTime.parse(updateRequest.getDate());

        lessonDao.update(Lesson.builder()
                .withId(lesson.getId())
                .withTeacher(teacher)
                .withGroup(group)
                .withDate(date)
                .withClassroom(classroom)
                .withCourse(course)
                .build());
    }
}
