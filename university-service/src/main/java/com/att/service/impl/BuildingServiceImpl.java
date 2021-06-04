package com.att.service.impl;

import com.att.exception.dao.BuildingNotFoundException;
import com.att.mapper.building.BuildingAddRequestMapper;
import com.att.mapper.building.BuildingUpdateRequestMapper;
import com.att.request.building.BuildingAddRequest;
import com.att.request.building.BuildingUpdateRequest;
import com.att.service.BuildingService;
import com.att.validator.building.BuildingAddValidator;
import com.att.validator.building.BuildingUpdateValidator;
import com.att.dao.BuildingRepository;
import com.att.entity.Building;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class BuildingServiceImpl implements BuildingService {
    private static final String BUILDING_RANK_NOT_FOUND = "BUILDING with Id %d is not found";

    private final BuildingRepository buildingRepository;
    private final BuildingUpdateValidator updateValidator;
    private final BuildingAddValidator addValidator;
    private final BuildingAddRequestMapper addRequestMapper;
    private final BuildingUpdateRequestMapper updateRequestMapper;

    @Override
    public List<Building> findAll() {
        log.debug("Find all Buildings...");

        return buildingRepository.findAll();
    }

    @Override
    public Building findById(Integer id) {
        return buildingRepository.findById(id)
                .orElseThrow(() -> new BuildingNotFoundException(String.format(BUILDING_RANK_NOT_FOUND, id)));
    }

    @Override
    @Transactional
    public void create(BuildingAddRequest addRequest) {
        log.debug("Building creating with request {}", addRequest);

        addValidator.validate(addRequest);

        buildingRepository.save(addRequestMapper.convertToEntity(addRequest));
    }

    @Override
    @Transactional
    public void update(BuildingUpdateRequest updateRequest) {
        updateValidator.validate(updateRequest);

        if (!buildingRepository.findById(updateRequest.getId()).isPresent()) {
            throw new BuildingNotFoundException(String.format(BUILDING_RANK_NOT_FOUND, updateRequest.getId()));
        }

        buildingRepository.save(updateRequestMapper.convertToEntity(updateRequest));
    }

    @Override
    @Transactional
    public void deleteById(Integer id) {
        if (!buildingRepository.findById(id).isPresent()) {
            throw new BuildingNotFoundException(String.format(BUILDING_RANK_NOT_FOUND, id));
        }

        log.debug("Building deleting with id {}", id);

        buildingRepository.deleteById(id);
    }
}
