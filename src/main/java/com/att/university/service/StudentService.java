package com.att.university.service;

import com.att.university.entity.Student;

public interface StudentService {
    void register(Student student);

    void update(Student student);

    boolean login(String email, String password);
}
