package com.att.service.impl;

import com.att.dao.ClassroomRepository;
import com.att.dao.CourseRepository;
import com.att.dao.GroupRepository;
import com.att.dao.LessonRepository;
import com.att.dao.TeacherRepository;
import com.att.entity.Classroom;
import com.att.entity.Course;
import com.att.entity.Group;
import com.att.entity.Lesson;
import com.att.entity.Teacher;
import com.att.exception.dao.LessonNotFoundException;
import com.att.mapper.lesson.LessonAddRequestMapper;
import com.att.mapper.lesson.LessonUpdateRequestMapper;
import com.att.request.lesson.LessonAddRequest;
import com.att.request.lesson.LessonUpdateRequest;
import com.att.service.LessonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.sql.Timestamp;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Transactional
@Validated
public class LessonServiceImpl implements LessonService {
    private static final String LESSON_NOT_FOUND = "Lesson with Id %d is not found";

    private final TeacherRepository teacherRepository;
    private final LessonRepository lessonRepository;
    private final CourseRepository courseRepository;
    private final GroupRepository groupRepository;
    private final ClassroomRepository classroomRepository;
    private final LessonAddRequestMapper lessonAddRequestMapper;
    private final LessonUpdateRequestMapper lessonUpdateRequestMapper;

    @Override
    public void add(LessonAddRequest addRequest) {
        log.debug("Adding lesson with request {}", addRequest);

        Course course = courseRepository.findById(addRequest.getCourseId())
                .orElseThrow(() -> new LessonNotFoundException("Course is not found"));

        Classroom classroom = classroomRepository.findById(addRequest.getClassroomId())
                .orElseThrow(() -> new LessonNotFoundException("Classroom is not found"));

        Group group = groupRepository.findById(addRequest.getGroupId())
                .orElseThrow(() -> new LessonNotFoundException("Group is not found"));

        Teacher teacher = teacherRepository.findById(addRequest.getTeacherId())
                .orElseThrow(() -> new LessonNotFoundException("Teacher is not found"));

        lessonRepository.save(lessonAddRequestMapper.convertToEntity(addRequest, course, group, teacher, classroom));
    }

    @Override
    public void update(LessonUpdateRequest updateRequest) {
        log.debug("Updating lesson with request {}", updateRequest);

        if (!lessonRepository.findById(updateRequest.getId()).isPresent()) {
            throw new LessonNotFoundException(String.format(LESSON_NOT_FOUND, updateRequest.getId()));
        }

        Course course = courseRepository.findById(updateRequest.getCourseId())
                .orElseThrow(() -> new LessonNotFoundException("Course is not found"));

        Classroom classroom = classroomRepository.findById(updateRequest.getClassroomId())
                .orElseThrow(() -> new LessonNotFoundException("Classroom is not found"));

        Group group = groupRepository.findById(updateRequest.getGroupId())
                .orElseThrow(() -> new LessonNotFoundException("Group is not found"));

        Teacher teacher = teacherRepository.findById(updateRequest.getTeacherId())
                .orElseThrow(() -> new LessonNotFoundException("Teacher is not found"));

        lessonRepository.save(lessonUpdateRequestMapper.convertToEntity(updateRequest, course, group, teacher,
                classroom));
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
