package com.att.university.service;

import com.att.university.entity.Lesson;
import com.att.university.request.lesson.LessonAddRequest;
import com.att.university.request.lesson.LessonUpdateRequest;

import java.time.LocalDate;
import java.util.List;

public interface LessonService {
    void add(LessonAddRequest addRequest);

    void update(LessonUpdateRequest updateRequest);

    Lesson findById(Integer id);

    void deleteById(Integer id);

    List<LocalDate> findTeacherLessonWeeks(LocalDate startDate, LocalDate endDate, Integer teacherId);

    List<Lesson> findByDateBetween(LocalDate startDate, LocalDate endDate);

    List<Lesson> findTeacherWeekSchedule(int currentPage, List<LocalDate> weeks, Integer teacherId);

    List<Lesson> findByDateBetweenAndTeacherId(LocalDate startDate, LocalDate endDate, Integer teacherId);
}
