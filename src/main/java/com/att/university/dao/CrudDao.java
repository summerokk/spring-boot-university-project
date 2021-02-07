package com.att.university.dao;

import java.util.List;

public interface CrudDao<E, I> {
    void save(E entity);

    void saveAll(List<E> entities);

    E findById(I id);

    void deleteById(I id);

    List<E> findAll(int page, int count);

    void update(E entity);

    int count();
}
