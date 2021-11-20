package com.att.mapper.sciencedegree;

import com.att.entity.ScienceDegree;
import com.att.request.science_degree.ScienceDegreeAddRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ScienceDegreeAddRequestMapper {
    ScienceDegree convertToEntity(ScienceDegreeAddRequest addRequest);
}
