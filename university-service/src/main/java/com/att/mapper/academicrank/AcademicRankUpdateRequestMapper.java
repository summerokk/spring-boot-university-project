package com.att.mapper.academicrank;

import com.att.request.academic_rank.AcademicRankUpdateRequest;
import com.att.entity.AcademicRank;
import org.springframework.stereotype.Component;

@Component
public class AcademicRankUpdateRequestMapper {
    public AcademicRank convertToEntity(AcademicRankUpdateRequest updateRequest) {
        return new AcademicRank(updateRequest.getId(), updateRequest.getName());
    }
}
