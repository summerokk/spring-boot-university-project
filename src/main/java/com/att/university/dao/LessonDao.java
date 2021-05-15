package com.att.university.dao;

import com.att.university.entity.Lesson;

import java.time.LocalDateTime;
import java.util.List;

public interface LessonDao extends CrudDao<Lesson, Integer> {
    List<Lesson> findByDateBetween(LocalDateTime startDate, LocalDateTime endDate);

    List<Lesson> findByDateBetweenAndTeacherId(Integer teacherId, LocalDateTime startDate, LocalDateTime endDate);

    List<LocalDateTime> findTeacherLessonWeeks(LocalDateTime startDate, LocalDateTime endDate, Integer teacherId);
}
