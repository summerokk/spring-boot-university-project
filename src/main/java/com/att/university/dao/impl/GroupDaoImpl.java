package com.att.university.dao.impl;

import com.att.university.dao.GroupDao;
import com.att.university.entity.Group;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.Optional;

@Repository("groupDao")
public class GroupDaoImpl extends AbstractDaoImpl<Group> implements GroupDao {
    private static final String FIND_ALL_QUERY = "select b from Group b order by id asc";
    private static final String DELETE_BY_ID_QUERY = "delete from Group where id = :id";
    private static final String COUNT_QUERY = "select count(id) from Group";

    @Autowired
    public GroupDaoImpl(EntityManager entityManager, @Value("${hibernate.batch_size}") int batchSize) {
        super(entityManager, FIND_ALL_QUERY, DELETE_BY_ID_QUERY, COUNT_QUERY, batchSize);
    }

    @Override
    public Optional<Group> findById(Integer id) {
        return Optional.ofNullable(entityManager.find(Group.class, id));
    }
}
