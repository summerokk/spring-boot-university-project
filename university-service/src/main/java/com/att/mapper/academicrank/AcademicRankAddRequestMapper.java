package com.att.mapper.academicrank;

import com.att.request.academic_rank.AcademicRankAddRequest;
import com.att.entity.AcademicRank;
import org.springframework.stereotype.Component;

@Component
public class AcademicRankAddRequestMapper {
    public AcademicRank convertToEntity(AcademicRankAddRequest addRequest) {
        return new AcademicRank(null, addRequest.getName());
    }
}
