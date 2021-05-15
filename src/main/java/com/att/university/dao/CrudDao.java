package com.att.university.dao;

import java.util.List;
import java.util.Optional;

public interface CrudDao<E, I> {
    void save(E entity);

    void saveAll(List<E> entities);

    void update(E entity);

    Optional<E> findById(I id);

    void deleteById(I id);

    List<E> findAll(int page, int count);

    List<E> findAll();

    int count();
}
