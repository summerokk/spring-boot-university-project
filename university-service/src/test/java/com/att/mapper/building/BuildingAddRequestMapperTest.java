package com.att.mapper.building;

import com.att.entity.Building;
import com.att.request.building.BuildingAddRequest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BuildingAddRequestMapperTest {
    private final BuildingAddRequestMapper requestMapper = new BuildingAddRequestMapper();

    @Test
    void convertToEntityShouldReturnEntityWhenRequestContainsAllFields() {
        BuildingAddRequest addRequest = new BuildingAddRequest("test");

        Building building = requestMapper.convertToEntity(addRequest);

        assertEquals("test", building.getAddress());
    }
}
