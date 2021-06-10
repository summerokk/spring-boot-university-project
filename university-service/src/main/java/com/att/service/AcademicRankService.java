package com.att.service;

import com.att.request.academic_rank.AcademicRankAddRequest;
import com.att.request.academic_rank.AcademicRankUpdateRequest;
import com.att.entity.AcademicRank;

import javax.validation.Valid;
import java.util.List;

public interface AcademicRankService {
    List<AcademicRank> findAll();

    AcademicRank findById(Integer id);

    void update(@Valid AcademicRankUpdateRequest updateRequest);

    void create(@Valid AcademicRankAddRequest addRequest);

    void deleteById(Integer id);
}
