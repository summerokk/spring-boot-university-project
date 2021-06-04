package com.att.mapper.science_degree;

import com.att.request.science_degree.ScienceDegreeAddRequest;
import com.att.entity.ScienceDegree;
import org.springframework.stereotype.Component;

@Component
public class ScienceDegreeAddRequestMapper {
    public ScienceDegree convertToEntity(ScienceDegreeAddRequest addRequest) {
        return new ScienceDegree(null, addRequest.getName());
    }
}
