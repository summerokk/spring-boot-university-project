package com.att.mapper.academicrank;

import com.att.entity.AcademicRank;
import com.att.request.academic_rank.AcademicRankUpdateRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AcademicRankUpdateRequestMapper {
    AcademicRank convertToEntity(AcademicRankUpdateRequest updateRequest);
}
