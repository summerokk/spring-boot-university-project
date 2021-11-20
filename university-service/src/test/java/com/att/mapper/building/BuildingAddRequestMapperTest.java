package com.att.mapper.building;

import com.att.entity.Building;
import com.att.request.building.BuildingAddRequest;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class BuildingAddRequestMapperTest {
    private final BuildingAddRequestMapper mapper = Mappers.getMapper(BuildingAddRequestMapper.class);

    @Test
    void convertToEntityShouldReturnEntityWhenRequestContainsAllFields() {
        BuildingAddRequest addRequest = new BuildingAddRequest("test");

        Building building = mapper.convertToEntity(addRequest);

        assertEquals("test", building.getAddress());
    }

    @Test
    void convertToEntityShouldReturnNullWhenRequestIsNull() {
        Building building = mapper.convertToEntity(null);

        assertNull(building);
    }
}
