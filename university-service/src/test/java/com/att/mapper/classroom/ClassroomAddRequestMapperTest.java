package com.att.mapper.classroom;

import com.att.entity.Building;
import com.att.entity.Classroom;
import com.att.request.classroom.ClassroomAddRequest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ClassroomAddRequestMapperTest {
    private final ClassroomAddRequestMapper requestMapper = new ClassroomAddRequestMapper();

    @Test
    void convertToEntityShouldReturnEntityWhenRequestContainsAllFields() {
        Building building = new Building(12, "Building st");

        ClassroomAddRequest request = new ClassroomAddRequest(11, 12);

        Classroom classroom = requestMapper.convertToEntity(request, building);

        assertEquals(building, classroom.getBuilding());
    }
}
