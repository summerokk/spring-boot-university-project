package com.att.university.dao.impl;

import com.att.university.dao.FacultyDao;
import com.att.university.entity.Faculty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.Optional;

@Repository("facultyDao")
public class FacultyDaoImpl extends AbstractDaoImpl<Faculty> implements FacultyDao {
    private static final String FIND_ALL_QUERY = "select b from Faculty b order by id asc";
    private static final String DELETE_BY_ID_QUERY = "delete from Faculty where id = :id";
    private static final String COUNT_QUERY = "select count(id) from Faculty";

    @Autowired
    public FacultyDaoImpl(EntityManager entityManager,
                          @Value("${spring.jpa.properties.hibernate.jdbc.batch_size}") int batchSize) {
        super(entityManager, FIND_ALL_QUERY, DELETE_BY_ID_QUERY, COUNT_QUERY, batchSize);
    }

    @Override
    public Optional<Faculty> findById(Integer id) {
        return Optional.ofNullable(entityManager.find(Faculty.class, id));
    }
}
