package com.att.university.dao.impl;

import com.att.university.dao.CrudDao;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Slf4j
public abstract class AbstractDaoImpl<E> implements CrudDao<E, Integer> {
    protected EntityManager entityManager;

    private final String findAllQuery;
    private final String deleteByIdQuery;
    private final String countQuery;
    private final int batchSize;

    @Override
    public void save(E entity) {
        log.debug("Save {} in database", entity);

        entityManager.persist(entity);
    }

    @Override
    public void saveAll(List<E> entity) {
        log.debug("Save all {} in database", entity);

        for (int i = 0; i < entity.size(); i++) {
            if (i > 0 && i % batchSize == 0) {
                entityManager.flush();
                entityManager.clear();
            }

            entityManager.persist(entity.get(i));
        }
    }

    @Override
    public void update(E entity) {
        log.debug("Update entity {}", entity);

        entityManager.merge(entity);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<E> findAll(int page, int count) {
        log.debug("Find All, page {}, count {}", page, count);
        page = (page - 1) * count;

        Query query = entityManager.createQuery(findAllQuery);

        query.setFirstResult(page);
        query.setMaxResults(count);

        return query.getResultList();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<E> findAll() {
        log.debug("Find All in database");

        Query query = entityManager.createQuery(findAllQuery);
        return query.getResultList();
    }

    @Override
    public void deleteById(Integer id) {
        log.debug("Delete by id {} from database", id);

        entityManager.createQuery(deleteByIdQuery)
                .setParameter("id", id)
                .executeUpdate();
    }

    @Override
    public int count() {
        log.debug("Get row count from database");

        Query query = entityManager.createQuery(countQuery);
        Long result = (Long) query.getSingleResult();

        return result.intValue();
    }
}
