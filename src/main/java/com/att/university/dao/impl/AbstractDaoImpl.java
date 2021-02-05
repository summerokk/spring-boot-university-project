package com.att.university.dao.impl;

import com.att.university.dao.CrudDao;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.List;

public abstract class AbstractDaoImpl<E> implements CrudDao<E, Integer> {
    protected JdbcTemplate jdbcTemplate;
    private final RowMapper<E> rowMapper;
    private final String findByIdQuery;
    private final String findAllQuery;
    private final String deleteByIdQuery;

    protected AbstractDaoImpl(RowMapper<E> rowMapper, String findByIdQuery, String findAllQuery, String deleteByIdQuery) {
        this.rowMapper = rowMapper;
        this.findByIdQuery = findByIdQuery;
        this.findAllQuery = findAllQuery;
        this.deleteByIdQuery = deleteByIdQuery;
    }

    @Override
    public E findById(Integer id) {
        return this.jdbcTemplate.queryForObject(findByIdQuery, rowMapper, id);
    }

    @Override
    public List<E> findAll() {
        return this.jdbcTemplate.query(findAllQuery, rowMapper);
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

    protected abstract void insert(E entity);
}
