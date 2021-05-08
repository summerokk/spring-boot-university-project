package com.att.university.mapper.academic_rank;

import com.att.university.entity.AcademicRank;
import com.att.university.request.academic_rank.AcademicRankAddRequest;
import org.springframework.stereotype.Component;

@Component
public class AcademicRankAddRequestMapper {
    public AcademicRank convertToEntity(AcademicRankAddRequest addRequest) {
        return new AcademicRank(null, addRequest.getName());
    }
}
