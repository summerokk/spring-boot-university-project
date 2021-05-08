package com.att.university.mapper.science_degree;

import com.att.university.entity.ScienceDegree;
import com.att.university.request.science_degree.ScienceDegreeUpdateRequest;
import org.springframework.stereotype.Component;

@Component
public class ScienceDegreeUpdateRequestMapper {
    public ScienceDegree convertToEntity(ScienceDegreeUpdateRequest updateRequest) {
        return new ScienceDegree(updateRequest.getId(), updateRequest.getName());
    }
}
