package com.att.university.service;

import com.att.university.entity.Teacher;
import com.att.university.request.person.teacher.TeacherRegisterRequest;
import com.att.university.request.person.teacher.TeacherUpdateRequest;

import java.util.List;

public interface TeacherService {
    void register(TeacherRegisterRequest student);

    void update(TeacherUpdateRequest student);

    boolean login(String email, String password);

    List<Teacher> findAll(int page, int count);

    List<Teacher> findAll();

    Teacher findById(Integer id);

    Teacher findByEmail(String email);

    int count();

    void deleteById(Integer id);
}
