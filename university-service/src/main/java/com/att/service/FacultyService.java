package com.att.service;

import com.att.request.faculty.FacultyAddRequest;
import com.att.request.faculty.FacultyUpdateRequest;
import com.att.entity.Faculty;

import java.util.List;

public interface FacultyService {
    List<Faculty> findAll();

    Faculty findById(Integer id);

    void update(FacultyUpdateRequest updateRequest);

    void create(FacultyAddRequest addRequest);

    void deleteById(Integer id);
}
