package com.att.university.service;

import com.att.university.entity.ScienceDegree;
import com.att.university.request.science_degree.ScienceDegreeAddRequest;
import com.att.university.request.science_degree.ScienceDegreeUpdateRequest;

import java.util.List;

public interface ScienceDegreeService {
    List<ScienceDegree> findAll();

    ScienceDegree findById(Integer id);

    void update(ScienceDegreeUpdateRequest updateRequest);

    void create(ScienceDegreeAddRequest addRequest);

    void deleteById(Integer id);
}
