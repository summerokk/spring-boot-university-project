package com.att.university.service;

import com.att.university.entity.Student;
import com.att.university.request.person.student.StudentRegisterRequest;
import com.att.university.request.person.student.StudentUpdateRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface StudentService {
    void register(StudentRegisterRequest student);

    void update(StudentUpdateRequest student);

    boolean login(String email, String password);

    void delete(Integer id);

    Student findById(Integer id);

    Student findByEmail(String email);

    Page<Student> findAll(Pageable pageable);
}
