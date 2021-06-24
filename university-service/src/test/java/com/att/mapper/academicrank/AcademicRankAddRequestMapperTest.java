package com.att.mapper.academicrank;

import com.att.entity.AcademicRank;
import com.att.request.academic_rank.AcademicRankAddRequest;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class AcademicRankAddRequestMapperTest {
    private final AcademicRankAddRequestMapper requestMapper = Mappers.getMapper(AcademicRankAddRequestMapper.class);

    @Test
    void convertToEntityShouldReturnEntityWhenRequestContainsAllFields() {
        AcademicRankAddRequest addRequest = new AcademicRankAddRequest("test");

        AcademicRank academicRank = requestMapper.convertToEntity(addRequest);

        assertEquals("test", academicRank.getName());
    }

    @Test
    void convertToEntityShouldReturnNullWhenRequestIsNull() {
        AcademicRank academicRank = requestMapper.convertToEntity(null);

        assertNull(academicRank);
    }
}
