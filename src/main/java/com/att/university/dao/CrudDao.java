package com.att.university.dao;

import java.util.List;

public interface CrudDao<E, ID> {
    void save(E entity);

    void saveAll(List<E> entities);

    E findById(ID id);

    void deleteById(ID id);

    List<E> findAll();

    void update(E entity);
}
