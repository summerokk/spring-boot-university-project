package com.att.university.service;

import com.att.university.entity.Student;
import com.att.university.request.person.student.StudentRegisterRequest;
import com.att.university.request.person.student.StudentUpdateRequest;

import java.util.List;

public interface StudentService {
    void register(StudentRegisterRequest student);

    void update(StudentUpdateRequest student);

    boolean login(String email, String password);

    void delete(Integer id);

    Student findById(Integer id);

    Student findByEmail(String email);

    List<Student> findAll(int page, int count);

    int count();
}
