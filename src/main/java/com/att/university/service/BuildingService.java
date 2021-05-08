package com.att.university.service;

import com.att.university.entity.Building;
import com.att.university.request.building.BuildingAddRequest;
import com.att.university.request.building.BuildingUpdateRequest;

import java.util.List;

public interface BuildingService {
    List<Building> findAll();

    Building findById(Integer id);

    void update(BuildingUpdateRequest updateRequest);

    void create(BuildingAddRequest addRequest);

    void deleteById(Integer id);
}
