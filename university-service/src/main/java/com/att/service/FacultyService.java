package com.att.service;

import com.att.request.faculty.FacultyAddRequest;
import com.att.request.faculty.FacultyUpdateRequest;
import com.att.entity.Faculty;

import javax.validation.Valid;
import java.util.List;

public interface FacultyService {
    List<Faculty> findAll();

    Faculty findById(Integer id);

    void update(@Valid FacultyUpdateRequest updateRequest);

    void create(@Valid FacultyAddRequest addRequest);

    void deleteById(Integer id);
}
