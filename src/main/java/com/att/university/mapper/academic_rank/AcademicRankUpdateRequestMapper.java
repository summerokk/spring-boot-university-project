package com.att.university.mapper.academic_rank;

import com.att.university.entity.AcademicRank;
import com.att.university.request.academic_rank.AcademicRankUpdateRequest;
import org.springframework.stereotype.Component;

@Component
public class AcademicRankUpdateRequestMapper {
    public AcademicRank convertToEntity(AcademicRankUpdateRequest updateRequest) {
        return new AcademicRank(updateRequest.getId(), updateRequest.getName());
    }
}
