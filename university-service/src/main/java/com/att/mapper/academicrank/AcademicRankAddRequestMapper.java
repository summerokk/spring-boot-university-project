package com.att.mapper.academicrank;

import com.att.entity.AcademicRank;
import com.att.request.academic_rank.AcademicRankAddRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AcademicRankAddRequestMapper {
    AcademicRank convertToEntity(AcademicRankAddRequest addRequest);
}
