package com.att.university.dao.impl;

import com.att.university.dao.ScienceDegreeDao;
import com.att.university.entity.ScienceDegree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.Optional;

@Repository("scienceDegreeDao")
public class ScienceDegreeDaoImpl extends AbstractDaoImpl<ScienceDegree> implements ScienceDegreeDao {
    private static final String FIND_ALL_QUERY = "select b from ScienceDegree b order by id asc";
    private static final String DELETE_BY_ID_QUERY = "delete from ScienceDegree where id = :id";
    private static final String COUNT_QUERY = "select count(id) from ScienceDegree";

    @Autowired
    public ScienceDegreeDaoImpl(EntityManager entityManager,
                                @Value("${spring.jpa.properties.hibernate.jdbc.batch_size}") int batchSize) {
        super(entityManager, FIND_ALL_QUERY, DELETE_BY_ID_QUERY, COUNT_QUERY, batchSize);
    }

    @Override
    public Optional<ScienceDegree> findById(Integer id) {
        return Optional.ofNullable(entityManager.find(ScienceDegree.class, id));
    }
}
