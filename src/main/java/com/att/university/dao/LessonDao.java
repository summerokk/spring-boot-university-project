package com.att.university.dao;

import com.att.university.entity.Lesson;

import java.time.LocalDate;
import java.util.List;

public interface LessonDao extends CrudDao<Lesson, Integer> {
    List<Lesson> findByDateBetween(LocalDate startDate, LocalDate endDate);

    List<Lesson> findByDateBetweenAndTeacherId(Integer teacherId, LocalDate startDate, LocalDate endDate);

    List<Lesson> findTeacherWeekSchedule(LocalDate startDate, Integer teacherId);

    List<LocalDate> findTeacherLessonWeeks(LocalDate startDate, LocalDate endDate, Integer teacherId);
}
