package com.att.service;

import com.att.request.person.teacher.TeacherRegisterRequest;
import com.att.request.person.teacher.TeacherUpdateRequest;
import com.att.entity.Teacher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.validation.Valid;
import java.util.List;

public interface TeacherService {
    void register(@Valid TeacherRegisterRequest request);

    void update(@Valid TeacherUpdateRequest request);

    boolean login(String email, String password);

    Page<Teacher> findAll(Pageable pageable);

    List<Teacher> findAll();

    Teacher findById(Integer id);

    Teacher findByEmail(String email);

    void deleteById(Integer id);
}
