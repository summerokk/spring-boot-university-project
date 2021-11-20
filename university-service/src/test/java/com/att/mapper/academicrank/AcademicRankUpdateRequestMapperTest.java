package com.att.mapper.academicrank;

import com.att.entity.AcademicRank;
import com.att.request.academic_rank.AcademicRankUpdateRequest;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class AcademicRankUpdateRequestMapperTest {
    private final AcademicRankUpdateRequestMapper requestMapper =
            Mappers.getMapper(AcademicRankUpdateRequestMapper.class);

    @Test
    void convertToEntityShouldReturnEntityWhenRequestContainsAllFields() {
        AcademicRankUpdateRequest updateRequest = new AcademicRankUpdateRequest(1, "test");

        AcademicRank academicRank = requestMapper.convertToEntity(updateRequest);

        assertEquals(1, academicRank.getId());
        assertEquals("test", academicRank.getName());
    }

    @Test
    void convertToEntityShouldReturnNullWhenRequestIsNull() {
        AcademicRank academicRank = requestMapper.convertToEntity(null);

        assertNull(academicRank);
    }
}
