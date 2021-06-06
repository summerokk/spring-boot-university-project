package com.att.mapper.building;

import com.att.request.building.BuildingAddRequest;
import com.att.entity.Building;
import org.springframework.stereotype.Component;

@Component
public class BuildingAddRequestMapper {
    public Building convertToEntity(BuildingAddRequest addRequest) {
        return new Building(null, addRequest.getAddress());
    }
}
