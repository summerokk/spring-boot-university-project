package com.att.service;

import com.att.request.academic_rank.AcademicRankAddRequest;
import com.att.request.academic_rank.AcademicRankUpdateRequest;
import com.att.entity.AcademicRank;

import java.util.List;

public interface AcademicRankService {
    List<AcademicRank> findAll();

    AcademicRank findById(Integer id);

    void update(AcademicRankUpdateRequest updateRequest);

    void create(AcademicRankAddRequest addRequest);

    void deleteById(Integer id);
}
