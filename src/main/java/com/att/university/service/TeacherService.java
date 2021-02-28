package com.att.university.service;

import com.att.university.request.person.teacher.TeacherRegisterRequest;
import com.att.university.request.person.teacher.TeacherUpdateRequest;

public interface TeacherService {
    void register(TeacherRegisterRequest student);

    void update(TeacherUpdateRequest student);

    boolean login(String email, String password);
}
