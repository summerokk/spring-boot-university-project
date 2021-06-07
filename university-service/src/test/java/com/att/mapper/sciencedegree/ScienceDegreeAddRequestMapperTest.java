package com.att.mapper.sciencedegree;

import com.att.entity.ScienceDegree;
import com.att.request.science_degree.ScienceDegreeAddRequest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ScienceDegreeAddRequestMapperTest {
    private final ScienceDegreeAddRequestMapper requestMapper = new ScienceDegreeAddRequestMapper();

    @Test
    void convertToEntityShouldReturnEntityWhenRequestContainsAllFields() {
        ScienceDegreeAddRequest request = new ScienceDegreeAddRequest("Science");

        ScienceDegree scienceDegree = requestMapper.convertToEntity(request);

        assertEquals("Science", scienceDegree.getName());
    }
}
