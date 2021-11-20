package com.att.mapper.sciencedegree;

import com.att.entity.ScienceDegree;
import com.att.request.science_degree.ScienceDegreeUpdateRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ScienceDegreeUpdateRequestMapper {
    ScienceDegree convertToEntity(ScienceDegreeUpdateRequest updateRequest);
}
