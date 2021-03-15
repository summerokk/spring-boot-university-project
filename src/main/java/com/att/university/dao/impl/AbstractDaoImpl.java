package com.att.university.dao.impl;

import com.att.university.dao.CrudDao;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.Types;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Slf4j
public abstract class AbstractDaoImpl<E> implements CrudDao<E, Integer> {
    protected JdbcTemplate jdbcTemplate;
    private final RowMapper<E> rowMapper;
    private final String findByIdQuery;
    private final String findAllQuery;
    private final String deleteByIdQuery;
    private final String countQuery;

    @Override
    public Optional<E> findById(Integer id) {
        log.debug("Find by Id {}", id);

        return findByParam(id, findByIdQuery);
    }

    @Override
    public List<E> findAll(int page, int count) {
        log.debug("Find All, page {}, count {}", page, count);

        page = (page - 1) * count;

        return this.jdbcTemplate.query(findAllQuery, new Object[]{page, count}, new int[]{Types.INTEGER, Types.INTEGER},
                rowMapper);
    }

    protected <P> Optional<E> findByParam(P param, String query) {
        try {
            return Optional.ofNullable(this.jdbcTemplate.queryForObject(query, rowMapper, param));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public void save(E entity) {
        log.debug("Save {} in database", entity);

        insert(entity);
    }

    @Override
    public void saveAll(List<E> entities) {
        log.debug("Save all entities {} in database", entities);

        entities.forEach(this::insert);
    }

    @Override
    public void deleteById(Integer id) {
        log.debug("Delete by id from database {}", id);

        this.jdbcTemplate.update(deleteByIdQuery, id);
    }

    @Override
    public int count() {
        log.debug("Get count rows from database");

        return Optional.ofNullable(this.jdbcTemplate.queryForObject(countQuery, Integer.class))
                .orElse(0);
    }

    protected abstract void insert(E entity);
}
