package com.att.mapper.classroom;

import com.att.entity.Building;
import com.att.entity.Classroom;
import com.att.request.classroom.ClassroomUpdateRequest;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class ClassroomUpdateRequestMapperTest {
    private final ClassroomUpdateRequestMapper requestMapper = Mappers.getMapper(ClassroomUpdateRequestMapper.class);

    @Test
    void convertToEntityShouldReturnEntityWhenRequestContainsAllFields() {
        Building building = new Building(12, "Building st");

        ClassroomUpdateRequest request = new ClassroomUpdateRequest(1, 11, 12);

        Classroom classroom = requestMapper.convertToEntity(request, building);

        assertEquals(1, classroom.getId());
        assertEquals(building, classroom.getBuilding());
        assertEquals(11, classroom.getNumber());
    }

    @Test
    void convertToEntityShouldReturnNullWhenRequestAndBuildingAreNull() {
        Classroom classroom = requestMapper.convertToEntity(null, null);

        assertNull(classroom);
    }

    @Test
    void convertToEntityShouldReturnNullWhenRequestNull() {
        Building building = new Building(12, "Building st");

        Classroom classroom = requestMapper.convertToEntity(null, building);

        System.out.println(classroom);

        assertNull(classroom.getId());
        assertNull(classroom.getNumber());
        assertEquals(building, classroom.getBuilding());
    }

    @Test
    void convertToEntityShouldReturnNullBuildingWhenBuildingIsNull() {
        ClassroomUpdateRequest request = new ClassroomUpdateRequest(1, 11, 12);

        Classroom classroom = requestMapper.convertToEntity(request, null);

        System.out.println(classroom);

        assertNull(classroom.getBuilding());
        assertEquals(1, classroom.getId());
        assertEquals(11, classroom.getNumber());
    }
}
