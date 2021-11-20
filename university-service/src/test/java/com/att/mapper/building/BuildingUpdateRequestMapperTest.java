package com.att.mapper.building;

import com.att.entity.Building;
import com.att.request.building.BuildingUpdateRequest;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class BuildingUpdateRequestMapperTest {
    private final BuildingUpdateRequestMapper mapper = Mappers.getMapper(BuildingUpdateRequestMapper.class);

    @Test
    void convertToEntityShouldReturnEntityWhenRequestContainsAllFields() {
        BuildingUpdateRequest UpdateRequest = new BuildingUpdateRequest(1, "test");

        Building building = mapper.convertToEntity(UpdateRequest);

        assertEquals(1, building.getId());
        assertEquals("test", building.getAddress());
    }

    @Test
    void convertToEntityShouldReturnNullWhenRequestIsNull() {
        Building building = mapper.convertToEntity(null);

        assertNull(building);
    }
}
