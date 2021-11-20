package com.att.mapper.sciencedegree;

import com.att.entity.ScienceDegree;
import com.att.request.science_degree.ScienceDegreeUpdateRequest;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class ScienceDegreeUpdateRequestMapperTest {
    private final ScienceDegreeUpdateRequestMapper requestMapper =
            Mappers.getMapper(ScienceDegreeUpdateRequestMapper.class);

    @Test
    void convertToEntityShouldReturnEntityWhenRequestContainsAllFields() {
        ScienceDegreeUpdateRequest request = new ScienceDegreeUpdateRequest(1, "Science");

        ScienceDegree scienceDegree = requestMapper.convertToEntity(request);

        assertEquals(1, scienceDegree.getId());
        assertEquals("Science", scienceDegree.getName());
    }

    @Test
    void convertToEntityShouldReturnNullWhenRequestIsNull() {
        ScienceDegree scienceDegree = requestMapper.convertToEntity(null);

        assertNull(scienceDegree);
    }
}
