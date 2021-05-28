package com.att.university.dao.impl;

import com.att.university.dao.BuildingDao;
import com.att.university.entity.Building;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.Optional;

@Repository("buildingDao")
public class BuildingDaoImpl extends AbstractDaoImpl<Building> implements BuildingDao {
    private static final String FIND_ALL_QUERY = "select b from Building b order by id asc";
    private static final String DELETE_BY_ID_QUERY = "delete from Building where id = :id";
    private static final String COUNT_QUERY = "select count(id) from Building";

    @Autowired
    public BuildingDaoImpl(EntityManager entityManager,
                           @Value("${spring.jpa.properties.hibernate.jdbc.batch_size}") int batchSize) {
        super(entityManager, FIND_ALL_QUERY, DELETE_BY_ID_QUERY, COUNT_QUERY, batchSize);
    }

    @Override
    public Optional<Building> findById(Integer id) {
        return Optional.ofNullable(entityManager.find(Building.class, id));
    }
}
