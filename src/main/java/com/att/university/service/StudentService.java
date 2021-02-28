package com.att.university.service;

import com.att.university.request.person.student.StudentRegisterRequest;
import com.att.university.request.person.student.StudentUpdateRequest;

public interface StudentService {
    void register(StudentRegisterRequest student);

    void update(StudentUpdateRequest student);

    boolean login(String email, String password);
}
