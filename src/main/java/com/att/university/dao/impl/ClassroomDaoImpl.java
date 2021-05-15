package com.att.university.dao.impl;

import com.att.university.dao.ClassroomDao;
import com.att.university.entity.Classroom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.Optional;

@Repository("classroomDao")
public class ClassroomDaoImpl extends AbstractDaoImpl<Classroom> implements ClassroomDao {
    private static final String FIND_ALL_QUERY = "select c from Classroom c order by id asc";
    private static final String DELETE_BY_ID_QUERY = "delete from Classroom where id = :id";
    private static final String COUNT_QUERY = "select count(id) from Classroom";

    @Autowired
    public ClassroomDaoImpl(EntityManager entityManager, @Value("${hibernate.batch_size}") int batchSize) {
        super(entityManager, FIND_ALL_QUERY, DELETE_BY_ID_QUERY, COUNT_QUERY, batchSize);
    }

    @Override
    public Optional<Classroom> findById(Integer id) {
        return Optional.ofNullable(entityManager.find(Classroom.class, id));
    }
}
