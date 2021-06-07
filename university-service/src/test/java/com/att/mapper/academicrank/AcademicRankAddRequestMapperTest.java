package com.att.mapper.academicrank;

import com.att.entity.AcademicRank;
import com.att.request.academic_rank.AcademicRankAddRequest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AcademicRankAddRequestMapperTest {
    private final AcademicRankAddRequestMapper requestMapper = new AcademicRankAddRequestMapper();

    @Test
    void convertToEntityShouldReturnEntityWhenRequestContainsAllFields() {
        AcademicRankAddRequest addRequest = new AcademicRankAddRequest("test");

        AcademicRank academicRank = requestMapper.convertToEntity(addRequest);

        assertEquals("test", academicRank.getName());
    }
}
