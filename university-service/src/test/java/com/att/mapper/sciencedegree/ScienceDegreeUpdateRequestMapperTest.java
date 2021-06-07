package com.att.mapper.sciencedegree;

import com.att.entity.ScienceDegree;
import com.att.request.science_degree.ScienceDegreeUpdateRequest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ScienceDegreeUpdateRequestMapperTest {
    private final ScienceDegreeUpdateRequestMapper requestMapper = new ScienceDegreeUpdateRequestMapper();

    @Test
    void convertToEntityShouldReturnEntityWhenRequestContainsAllFields() {
        ScienceDegreeUpdateRequest request = new ScienceDegreeUpdateRequest(1, "Science");

        ScienceDegree scienceDegree = requestMapper.convertToEntity(request);

        assertEquals("Science", scienceDegree.getName());
    }
}
