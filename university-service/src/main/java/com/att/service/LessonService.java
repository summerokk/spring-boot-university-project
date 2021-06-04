package com.att.service;

import com.att.request.lesson.LessonAddRequest;
import com.att.request.lesson.LessonUpdateRequest;
import com.att.entity.Lesson;

import java.time.LocalDateTime;
import java.util.List;

public interface LessonService {
    void add(LessonAddRequest addRequest);

    void update(LessonUpdateRequest updateRequest);

    Lesson findById(Integer id);

    void deleteById(Integer id);

    List<LocalDateTime> findTeacherLessonWeeks(LocalDateTime startDate, LocalDateTime endDate, Integer teacherId);

    List<Lesson> findByDateBetween(LocalDateTime startDate, LocalDateTime endDate);

    List<Lesson> findTeacherWeekSchedule(int currentPage, List<LocalDateTime> weeks, Integer teacherId);

    List<Lesson> findByDateBetweenAndTeacherId(LocalDateTime startDate, LocalDateTime endDate, Integer teacherId);
}
