package com.att.mapper.sciencedegree;

import com.att.entity.ScienceDegree;
import com.att.request.science_degree.ScienceDegreeAddRequest;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class ScienceDegreeAddRequestMapperTest {
    private final ScienceDegreeAddRequestMapper requestMapper = Mappers.getMapper(ScienceDegreeAddRequestMapper.class);

    @Test
    void convertToEntityShouldReturnEntityWhenRequestContainsAllFields() {
        ScienceDegreeAddRequest request = new ScienceDegreeAddRequest("Science");

        ScienceDegree scienceDegree = requestMapper.convertToEntity(request);

        assertEquals("Science", scienceDegree.getName());
    }

    @Test
    void convertToEntityShouldReturnNullWhenRequestIsNull() {
        ScienceDegree scienceDegree = requestMapper.convertToEntity(null);

        assertNull(scienceDegree);
    }
}
