package com.att.university.service;

import com.att.university.entity.Faculty;
import com.att.university.request.faculty.FacultyAddRequest;
import com.att.university.request.faculty.FacultyUpdateRequest;

import java.util.List;

public interface FacultyService {
    List<Faculty> findAll();

    Faculty findById(Integer id);

    void update(FacultyUpdateRequest updateRequest);

    void create(FacultyAddRequest addRequest);

    void deleteById(Integer id);
}
