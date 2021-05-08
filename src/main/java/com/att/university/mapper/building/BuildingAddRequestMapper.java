package com.att.university.mapper.building;

import com.att.university.entity.Building;
import com.att.university.request.building.BuildingAddRequest;
import org.springframework.stereotype.Component;

@Component
public class BuildingAddRequestMapper {
    public Building convertToEntity(BuildingAddRequest addRequest) {
        return new Building(null, addRequest.getAddress());
    }
}
