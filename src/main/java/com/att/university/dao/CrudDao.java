package com.att.university.dao;

import java.util.List;
import java.util.Optional;

public interface CrudDao<E, I> {
    void save(E entity);

    void saveAll(List<E> entities);

    Optional<E> findById(I id);

    void deleteById(I id);

    List<E> findAll(int page, int count);

    void update(E entity);

    int count();
}
