package com.att.university.dao.impl;

import com.att.university.dao.CrudDao;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.Types;
import java.util.List;
import java.util.Optional;

public abstract class AbstractDaoImpl<E> implements CrudDao<E, Integer> {
    protected JdbcTemplate jdbcTemplate;
    private final RowMapper<E> rowMapper;
    private final String findByIdQuery;
    private final String findAllQuery;
    private final String deleteByIdQuery;
    private final String countQuery;

    protected AbstractDaoImpl(DataSource dataSource, RowMapper<E> rowMapper, String findByIdQuery, String findAllQuery,
                              String deleteByIdQuery, String countQuery) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.rowMapper = rowMapper;
        this.findByIdQuery = findByIdQuery;
        this.findAllQuery = findAllQuery;
        this.deleteByIdQuery = deleteByIdQuery;
        this.countQuery = countQuery;
    }

    @Override
    public E findById(Integer id) {
        return this.jdbcTemplate.queryForObject(findByIdQuery, rowMapper, id);
    }

    @Override
    public List<E> findAll(int page, int count) {
        page = (page - 1) * count;

        return this.jdbcTemplate.query(findAllQuery, new Object[]{page, count}, new int[]{Types.INTEGER, Types.INTEGER},
                rowMapper);
    }

    @Override
    public void save(E entity) {
        insert(entity);
    }

    @Override
    public void saveAll(List<E> entities) {
        entities.forEach(this::insert);
    }

    @Override
    public void deleteById(Integer id) {
        this.jdbcTemplate.update(deleteByIdQuery, id);
    }

    @Override
    public int count() {
        Optional<Integer> count = Optional.ofNullable(this.jdbcTemplate.queryForObject(countQuery, Integer.class));
        return count.orElse(0);
    }

    protected abstract void insert(E entity);
}
