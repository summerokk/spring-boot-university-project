package com.att.service;

import com.att.request.science_degree.ScienceDegreeAddRequest;
import com.att.request.science_degree.ScienceDegreeUpdateRequest;
import com.att.entity.ScienceDegree;

import java.util.List;

public interface ScienceDegreeService {
    List<ScienceDegree> findAll();

    ScienceDegree findById(Integer id);

    void update(ScienceDegreeUpdateRequest updateRequest);

    void create(ScienceDegreeAddRequest addRequest);

    void deleteById(Integer id);
}
