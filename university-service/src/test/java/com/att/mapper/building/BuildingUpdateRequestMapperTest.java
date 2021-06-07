package com.att.mapper.building;

import com.att.entity.Building;
import com.att.request.building.BuildingUpdateRequest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BuildingUpdateRequestMapperTest {
    private final BuildingUpdateRequestMapper requestMapper = new BuildingUpdateRequestMapper();

    @Test
    void convertToEntityShouldReturnEntityWhenRequestContainsAllFields() {
        BuildingUpdateRequest UpdateRequest = new BuildingUpdateRequest(1, "test");

        Building building = requestMapper.convertToEntity(UpdateRequest);

        assertEquals("test", building.getAddress());
    }
}
