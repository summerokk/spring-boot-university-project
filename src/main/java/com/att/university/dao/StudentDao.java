package com.att.university.dao;

import com.att.university.entity.Student;

import java.util.Optional;

public interface StudentDao extends CrudDao<Student, Integer> {
    Optional<Student> findByEmail(String email);
}
