package com.att.mapper.classroom;

import com.att.entity.Building;
import com.att.entity.Classroom;
import com.att.request.classroom.ClassroomUpdateRequest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ClassroomUpdateRequestMapperTest {
    private final ClassroomUpdateRequestMapper requestMapper = new ClassroomUpdateRequestMapper();

    @Test
    void convertToEntityShouldReturnEntityWhenRequestContainsAllFields() {
        Building building = new Building(12, "Building st");

        ClassroomUpdateRequest request = new ClassroomUpdateRequest(1, 11, 12);

        Classroom classroom = requestMapper.convertToEntity(request, building);

        assertEquals(1, classroom.getId());
        assertEquals(building, classroom.getBuilding());
        assertEquals(11, classroom.getNumber());
    }
}
