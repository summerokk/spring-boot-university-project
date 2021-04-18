package com.att.university.service;

import com.att.university.entity.Lesson;
import com.att.university.request.lesson.LessonAddRequest;
import com.att.university.request.lesson.LessonUpdateRequest;

import java.time.LocalDate;
import java.util.List;

public interface LessonService {
    List<Lesson> findAll(int page, int count);

    void add(LessonAddRequest addRequest);

    void update(LessonUpdateRequest updateRequest);

    List<LocalDate> findTeacherLessonWeeks(LocalDate startDate, LocalDate endDate, Integer teacherId);

    List<Lesson> findTeacherWeekSchedule(int currentPage, List<LocalDate> weeks, Integer teacherId);

    List<Lesson> findByDateBetweenAndTeacherId(LocalDate startDate, LocalDate endDate, Integer teacherId);
}
