package com.att.university.service;

import com.att.university.entity.AcademicRank;
import com.att.university.request.academic_rank.AcademicRankAddRequest;
import com.att.university.request.academic_rank.AcademicRankUpdateRequest;

import java.util.List;

public interface AcademicRankService {
    List<AcademicRank> findAll();

    AcademicRank findById(Integer id);

    void update(AcademicRankUpdateRequest updateRequest);

    void create(AcademicRankAddRequest addRequest);

    void deleteById(Integer id);
}
