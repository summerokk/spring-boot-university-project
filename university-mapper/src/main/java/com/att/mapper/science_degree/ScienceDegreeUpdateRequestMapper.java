package com.att.mapper.science_degree;

import com.att.request.science_degree.ScienceDegreeUpdateRequest;
import com.att.entity.ScienceDegree;
import org.springframework.stereotype.Component;

@Component
public class ScienceDegreeUpdateRequestMapper {
    public ScienceDegree convertToEntity(ScienceDegreeUpdateRequest updateRequest) {
        return new ScienceDegree(updateRequest.getId(), updateRequest.getName());
    }
}
