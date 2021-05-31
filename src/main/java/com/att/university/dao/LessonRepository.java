package com.att.university.dao;

import com.att.university.entity.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, Integer> {
    List<Lesson> findByDateBetween(LocalDateTime startDate, LocalDateTime endDate);

    List<Lesson> findByDateBetweenAndTeacherId(LocalDateTime startDate, LocalDateTime endDate, Integer teacherId);

    @Query(
            value = "SELECT date_trunc('week', date) as week FROM lessons l WHERE date between ?1 and ?2 and " +
                    "teacher_id=?3 GROUP BY week ORDER BY week ASC",
            nativeQuery = true)
    List<Timestamp> findTeacherLessonWeeks(LocalDateTime startDate, LocalDateTime endDate, Integer teacherId);
}
