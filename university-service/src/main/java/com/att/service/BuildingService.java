package com.att.service;

import com.att.request.building.BuildingAddRequest;
import com.att.request.building.BuildingUpdateRequest;
import com.att.entity.Building;

import java.util.List;

public interface BuildingService {
    List<Building> findAll();

    Building findById(Integer id);

    void update(BuildingUpdateRequest updateRequest);

    void create(BuildingAddRequest addRequest);

    void deleteById(Integer id);
}
