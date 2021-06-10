package com.att.service;

import com.att.request.person.student.StudentRegisterRequest;
import com.att.request.person.student.StudentUpdateRequest;
import com.att.entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.validation.Valid;

public interface StudentService {
    void register(@Valid StudentRegisterRequest student);

    void update(@Valid StudentUpdateRequest student);

    boolean login(String email, String password);

    void delete(Integer id);

    Student findById(Integer id);

    Student findByEmail(String email);

    Page<Student> findAll(Pageable pageable);
}
