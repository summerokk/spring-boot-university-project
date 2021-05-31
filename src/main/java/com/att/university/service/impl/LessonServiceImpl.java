package com.att.university.service.impl;

import com.att.university.dao.ClassroomRepository;
import com.att.university.dao.CourseRepository;
import com.att.university.dao.GroupRepository;
import com.att.university.dao.LessonRepository;
import com.att.university.dao.TeacherRepository;
import com.att.university.entity.Classroom;
import com.att.university.entity.Course;
import com.att.university.entity.Group;
import com.att.university.entity.Lesson;
import com.att.university.entity.Teacher;
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

import java.sql.Timestamp;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Transactional
public class LessonServiceImpl implements LessonService {
    private static final String LESSON_NOT_FOUND = "Lesson with Id %d is not found";

    private final LessonAddValidator lessonAddValidator;
    private final LessonUpdateValidator lessonUpdateValidator;
    private final TeacherRepository teacherRepository;
    private final LessonRepository lessonRepository;
    private final CourseRepository courseRepository;
    private final GroupRepository groupRepository;
    private final ClassroomRepository classroomRepository;

    @Override
    public void add(LessonAddRequest addRequest) {
        lessonAddValidator.validate(addRequest);

        log.debug("Adding lesson with request {}", addRequest);

        Course course = courseRepository.findById(addRequest.getCourseId())
                .orElseThrow(() -> new LessonNotFoundException("Course is not found"));

        Classroom classroom = classroomRepository.findById(addRequest.getClassroomId())
                .orElseThrow(() -> new LessonNotFoundException("Classroom is not found"));

        Group group = groupRepository.findById(addRequest.getGroupId())
                .orElseThrow(() -> new LessonNotFoundException("Group is not found"));

        Teacher teacher = teacherRepository.findById(addRequest.getTeacherId())
                .orElseThrow(() -> new LessonNotFoundException("Teacher is not found"));

        lessonRepository.save(Lesson.builder()
                .withTeacher(teacher)
                .withGroup(group)
                .withDate(addRequest.getDate())
                .withClassroom(classroom)
                .withCourse(course)
                .build());
    }

    @Override
    public void update(LessonUpdateRequest updateRequest) {
        log.debug("Updating lesson with request {}", updateRequest);

        lessonUpdateValidator.validate(updateRequest);

        Lesson lesson = lessonRepository.findById(updateRequest.getId())
                .orElseThrow(() -> new LessonNotFoundException(String.format(LESSON_NOT_FOUND, updateRequest.getId())));

        Course course = courseRepository.findById(updateRequest.getCourseId())
                .orElseThrow(() -> new LessonNotFoundException("Course is not found"));

        Classroom classroom = classroomRepository.findById(updateRequest.getClassroomId())
                .orElseThrow(() -> new LessonNotFoundException("Classroom is not found"));

        Group group = groupRepository.findById(updateRequest.getGroupId())
                .orElseThrow(() -> new LessonNotFoundException("Group is not found"));

        Teacher teacher = teacherRepository.findById(updateRequest.getTeacherId())
                .orElseThrow(() -> new LessonNotFoundException("Teacher is not found"));

        lessonRepository.save(Lesson.builder()
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
        return lessonRepository.findById(id)
                .orElseThrow(() -> new LessonNotFoundException(String.format(LESSON_NOT_FOUND, id)));
    }

    @Override
    public List<LocalDateTime> findTeacherLessonWeeks(LocalDateTime startDate, LocalDateTime endDate, Integer teacherId) {
        return lessonRepository.findTeacherLessonWeeks(startDate, endDate, teacherId)
                .stream()
                .map(date -> Timestamp.valueOf(date.toString()).toLocalDateTime())
                .collect(Collectors.toList());
    }

    @Override
    public List<Lesson> findTeacherWeekSchedule(int currentPage, List<LocalDateTime> weeks, Integer teacherId) {
        LocalDateTime startDate = weeks.get(currentPage - 1);
        LocalDateTime endDate = startDate.with(DayOfWeek.SUNDAY);

        return lessonRepository.findByDateBetweenAndTeacherId(startDate, endDate, teacherId);
    }

    @Override
    public List<Lesson> findByDateBetweenAndTeacherId(LocalDateTime startDate, LocalDateTime endDate, Integer teacherId) {
        return lessonRepository.findByDateBetweenAndTeacherId(startDate, endDate, teacherId);
    }

    @Override
    public List<Lesson> findByDateBetween(LocalDateTime startDate, LocalDateTime endDate) {
        return lessonRepository.findByDateBetween(startDate, endDate);
    }

    @Override
    public void deleteById(Integer id) {
        if (!lessonRepository.findById(id).isPresent()) {
            throw new LessonNotFoundException(String.format(LESSON_NOT_FOUND, id));
        }

        log.debug("Lesson deleting with id {}", id);

        lessonRepository.deleteById(id);
    }
}
