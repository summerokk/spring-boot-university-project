package com.att.university.dao.impl;

import com.att.university.dao.CourseDao;
import com.att.university.entity.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.Optional;

@Repository("courseDao")
public class CourseDaoImpl extends AbstractDaoImpl<Course> implements CourseDao {
    private static final String FIND_ALL_QUERY = "select b from Course b order by id asc";
    private static final String DELETE_BY_ID_QUERY = "delete from Course where id = :id";
    private static final String COUNT_QUERY = "select count(id) from Course";

    @Autowired
    public CourseDaoImpl(EntityManager entityManager,
                         @Value("${spring.jpa.properties.hibernate.jdbc.batch_size}") int batchSize) {
        super(entityManager, FIND_ALL_QUERY, DELETE_BY_ID_QUERY, COUNT_QUERY, batchSize);
    }

    @Override
    public Optional<Course> findById(Integer id) {
        return Optional.ofNullable(entityManager.find(Course.class, id));
    }
}
