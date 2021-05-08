package com.att.university.mapper.building;

import com.att.university.entity.Building;
import com.att.university.request.building.BuildingUpdateRequest;
import org.springframework.stereotype.Component;

@Component
public class BuildingUpdateRequestMapper {
    public Building convertToEntity(BuildingUpdateRequest updateRequest) {
        return new Building(updateRequest.getId(), updateRequest.getAddress());
    }
}
