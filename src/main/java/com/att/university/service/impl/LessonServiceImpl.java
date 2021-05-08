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
import com.att.university.exception.dao.GroupNotFoundException;
import com.att.university.exception.dao.LessonNotFoundException;
import com.att.university.request.lesson.LessonAddRequest;
import com.att.university.request.lesson.LessonUpdateRequest;
import com.att.university.service.LessonService;
import com.att.university.validator.lesson.LessonAddValidator;
import com.att.university.validator.lesson.LessonUpdateValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class LessonServiceImpl implements LessonService {
    private static final String LESSON_NOT_FOUND = "Lesson with Id %d is not found";

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

        log.debug("Adding lesson with request {}", addRequest);

        Course course = courseDao.findById(addRequest.getCourseId())
                .orElseThrow(() -> new LessonNotFoundException("Course is not found"));

        Classroom classroom = classroomDao.findById(addRequest.getClassroomId())
                .orElseThrow(() -> new LessonNotFoundException("Classroom is not found"));

        Group group = groupDao.findById(addRequest.getGroupId())
                .orElseThrow(() -> new LessonNotFoundException("Group is not found"));

        Teacher teacher = teacherDao.findById(addRequest.getTeacherId())
                .orElseThrow(() -> new LessonNotFoundException("Teacher is not found"));

        lessonDao.save(Lesson.builder()
                .withTeacher(teacher)
                .withGroup(group)
                .withDate(addRequest.getDate())
                .withClassroom(classroom)
                .withCourse(course)
                .build());
    }

    @Override
    @Transactional
    public void update(LessonUpdateRequest updateRequest) {
        log.debug("Updating lesson with request {}", updateRequest);

        lessonUpdateValidator.validate(updateRequest);

        Lesson lesson = lessonDao.findById(updateRequest.getId())
                .orElseThrow(() -> new LessonNotFoundException(String.format(LESSON_NOT_FOUND, updateRequest.getId())));

        Course course = courseDao.findById(updateRequest.getCourseId())
                .orElseThrow(() -> new LessonNotFoundException("Course is not found"));

        Classroom classroom = classroomDao.findById(updateRequest.getClassroomId())
                .orElseThrow(() -> new LessonNotFoundException("Classroom is not found"));

        Group group = groupDao.findById(updateRequest.getGroupId())
                .orElseThrow(() -> new LessonNotFoundException("Group is not found"));

        Teacher teacher = teacherDao.findById(updateRequest.getTeacherId())
                .orElseThrow(() -> new LessonNotFoundException("Teacher is not found"));

        lessonDao.update(Lesson.builder()
                .withId(lesson.getId())
                .withTeacher(teacher)
                .withGroup(group)
                .withDate(updateRequest.getDate())
                .withClassroom(classroom)
                .withCourse(course)
                .build());
    }

    @Override
    public Lesson findById(Integer id) {
        return lessonDao.findById(id)
                .orElseThrow(() -> new LessonNotFoundException(String.format(LESSON_NOT_FOUND, id)));
    }

    @Override
    public List<LocalDate> findTeacherLessonWeeks(LocalDate startDate, LocalDate endDate, Integer teacherId) {
        List<LocalDate> weeks = lessonDao.findTeacherLessonWeeks(startDate, endDate, teacherId);
        weeks.set(0, startDate);

        return weeks;
    }

    @Override
    public List<Lesson> findTeacherWeekSchedule(int currentPage, List<LocalDate> weeks, Integer teacherId) {
        LocalDate startDate = weeks.get(currentPage - 1);
        LocalDate endDate = startDate.with(DayOfWeek.SUNDAY);

        return lessonDao.findByDateBetweenAndTeacherId(teacherId, startDate, endDate);
    }

    @Override
    public List<Lesson> findByDateBetweenAndTeacherId(LocalDate startDate, LocalDate endDate, Integer teacherId) {
        return lessonDao.findByDateBetweenAndTeacherId(teacherId, startDate, endDate);
    }

    @Override
    public List<Lesson> findByDateBetween(LocalDate startDate, LocalDate endDate) {
        return lessonDao.findByDateBetween(startDate, endDate);
    }

    @Override
    public void deleteById(Integer id) {
        if (!lessonDao.findById(id).isPresent()) {
            throw new LessonNotFoundException(String.format(LESSON_NOT_FOUND, id));
        }

        log.debug("Lesson deleting with id {}", id);

        lessonDao.deleteById(id);
    }
}
