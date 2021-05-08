package com.att.university.mapper.science_degree;

import com.att.university.entity.ScienceDegree;
import com.att.university.request.science_degree.ScienceDegreeAddRequest;
import org.springframework.stereotype.Component;

@Component
public class ScienceDegreeAddRequestMapper {
    public ScienceDegree convertToEntity(ScienceDegreeAddRequest addRequest) {
        return new ScienceDegree(null, addRequest.getName());
    }
}
