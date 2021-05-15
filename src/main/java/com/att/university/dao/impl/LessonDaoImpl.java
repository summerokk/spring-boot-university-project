package com.att.university.dao.impl;

import com.att.university.dao.LessonDao;
import com.att.university.entity.Lesson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository("lessonDao")
@Slf4j
public class LessonDaoImpl extends AbstractDaoImpl<Lesson> implements LessonDao {
    private static final String FIND_ALL_QUERY = "select l from Lesson l order by id asc";
    private static final String FIND_BY_DATES_QUERY = "select l from Lesson l WHERE l.date between :fd and :ld";
    private static final String FIND_BY_DATE_WITH_TEACHER_ID_QUERY = FIND_BY_DATES_QUERY + " AND teacher_id = :id ORDER BY date";
    private static final String DELETE_BY_ID_QUERY = "delete from Lesson where id = :id";
    private static final String COUNT_QUERY = "select count(id) from Lesson";
    private static final String FIND_TEACHER_LESSON_WEEKS_QUERY = "SELECT date_trunc('week', date) as week " +
            "FROM lessons WHERE teacher_id=:id and date between :sd and :ed GROUP BY week ORDER BY week ASC";

    @Autowired
    public LessonDaoImpl(EntityManager entityManager, @Value("${hibernate.batch_size}") int batchSize) {
        super(entityManager, FIND_ALL_QUERY, DELETE_BY_ID_QUERY, COUNT_QUERY, batchSize);
    }

    @Override
    public Optional<Lesson> findById(Integer id) {
        return Optional.ofNullable(entityManager.find(Lesson.class, id));
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Lesson> findByDateBetween(LocalDateTime startDate, LocalDateTime endDate) {
        log.debug("Find all lessons between dates {} and {}", startDate, endDate);

        Query query = entityManager.createQuery(FIND_BY_DATES_QUERY)
                .setParameter("fd", startDate)
                .setParameter("ld", endDate);

        return query.getResultList();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Lesson> findByDateBetweenAndTeacherId(Integer teacherId, LocalDateTime startDate, LocalDateTime endDate) {
        log.debug("Find all lessons between dates {} and {} and teacher id {}", startDate, endDate, teacherId);

        Query query = entityManager.createQuery(FIND_BY_DATE_WITH_TEACHER_ID_QUERY)
                .setParameter("fd", startDate)
                .setParameter("ld", endDate)
                .setParameter("id", teacherId);

        return query.getResultList();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<LocalDateTime> findTeacherLessonWeeks(LocalDateTime startDate, LocalDateTime endDate, Integer teacherId) {
        log.debug("Find all teacher weeks between dates {} and {} and teacher id {}", startDate, endDate, teacherId);

        Query query = entityManager.createNativeQuery(FIND_TEACHER_LESSON_WEEKS_QUERY);
        query.setParameter("id", teacherId);
        query.setParameter("sd", startDate);
        query.setParameter("ed", endDate);

        return (List<LocalDateTime>) query.getResultStream()
                .map(date -> Timestamp.valueOf(date.toString()).toLocalDateTime())
                .collect(Collectors.toList());
    }
}
