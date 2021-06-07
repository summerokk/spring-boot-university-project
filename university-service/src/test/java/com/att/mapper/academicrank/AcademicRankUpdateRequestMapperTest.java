package com.att.mapper.academicrank;

import com.att.entity.AcademicRank;
import com.att.request.academic_rank.AcademicRankUpdateRequest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AcademicRankUpdateRequestMapperTest {
    private final AcademicRankUpdateRequestMapper requestMapper = new AcademicRankUpdateRequestMapper();

    @Test
    void convertToEntityShouldReturnEntityWhenRequestContainsAllFields() {
        AcademicRankUpdateRequest updateRequest = new AcademicRankUpdateRequest(1, "test");

        AcademicRank academicRank = requestMapper.convertToEntity(updateRequest);

        assertEquals("test", academicRank.getName());
    }
}
