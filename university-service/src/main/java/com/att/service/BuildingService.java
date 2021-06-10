package com.att.service;

import com.att.request.building.BuildingAddRequest;
import com.att.request.building.BuildingUpdateRequest;
import com.att.entity.Building;

import javax.validation.Valid;
import java.util.List;

public interface BuildingService {
    List<Building> findAll();

    Building findById(Integer id);

    void update(@Valid BuildingUpdateRequest updateRequest);

    void create(@Valid BuildingAddRequest addRequest);

    void deleteById(Integer id);
}
