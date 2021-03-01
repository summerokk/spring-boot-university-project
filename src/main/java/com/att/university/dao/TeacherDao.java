package com.att.university.dao;

import com.att.university.entity.Teacher;

import java.util.Optional;

public interface TeacherDao extends CrudDao<Teacher, Integer> {
    Optional<Teacher> findByEmail(String email);
}
