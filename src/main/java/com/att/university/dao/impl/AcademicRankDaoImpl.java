package com.att.university.dao.impl;

import com.att.university.dao.AcademicRankDao;
import com.att.university.entity.AcademicRank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.Optional;

@Repository("academicRankDao")
public class AcademicRankDaoImpl extends AbstractDaoImpl<AcademicRank> implements AcademicRankDao {
    private static final String FIND_ALL_QUERY = "select b from AcademicRank b order by id asc";
    private static final String DELETE_BY_ID_QUERY = "delete from AcademicRank where id = :id";
    private static final String COUNT_QUERY = "select count(id) from AcademicRank";

    @Autowired
    public AcademicRankDaoImpl(EntityManager entityManager, @Value("${hibernate.batch_size}") int batchSize) {
        super(entityManager, FIND_ALL_QUERY, DELETE_BY_ID_QUERY, COUNT_QUERY, batchSize);
    }

    @Override
    public Optional<AcademicRank> findById(Integer id) {
        return Optional.ofNullable(entityManager.find(AcademicRank.class, id));
    }
}
