package com.att.service.impl;

import com.att.exception.dao.BuildingNotFoundException;
import com.att.exception.dao.ClassroomNotFoundException;
import com.att.mapper.classroom.ClassroomAddRequestMapper;
import com.att.mapper.classroom.ClassroomUpdateRequestMapper;
import com.att.request.classroom.ClassroomAddRequest;
import com.att.request.classroom.ClassroomUpdateRequest;
import com.att.validator.classroom.ClassroomAddValidator;
import com.att.validator.classroom.ClassroomUpdateValidator;
import com.att.dao.BuildingRepository;
import com.att.dao.ClassroomRepository;
import com.att.entity.Building;
import com.att.entity.Classroom;
import com.att.service.ClassroomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ClassroomServiceImpl implements ClassroomService {
    private static final String CLASSROOM_NOT_FOUND = "Classroom with Id %d is not found";
    private static final String BUILDING_RANK_NOT_FOUND = "Building with Id %d is not found";

    private final ClassroomRepository classroomRepository;
    private final BuildingRepository buildingRepository;
    private final ClassroomUpdateValidator updateValidator;
    private final ClassroomAddValidator addValidator;
    private final ClassroomAddRequestMapper addRequestMapper;
    private final ClassroomUpdateRequestMapper updateRequestMapper;

    @Override
    public List<Classroom> findAll() {
        return classroomRepository.findAll();
    }

    @Override
    public Classroom findById(Integer id) {
        return classroomRepository.findById(id)
                .orElseThrow(() -> new ClassroomNotFoundException(String.format(CLASSROOM_NOT_FOUND, id)));
    }

    @Override
    @Transactional
    public void create(ClassroomAddRequest addRequest) {
        log.debug("Classroom creating with request {}", addRequest);

        addValidator.validate(addRequest);

        Building building = buildingRepository.findById(addRequest.getBuildingId())
                .orElseThrow(() -> new BuildingNotFoundException(
                        String.format(BUILDING_RANK_NOT_FOUND, addRequest.getBuildingId())));

        classroomRepository.save(addRequestMapper.convertToEntity(addRequest, building));
    }

    @Override
    @Transactional
    public void update(ClassroomUpdateRequest updateRequest) {
        updateValidator.validate(updateRequest);

        if (!classroomRepository.findById(updateRequest.getId()).isPresent()) {
            throw new ClassroomNotFoundException(String.format(CLASSROOM_NOT_FOUND, updateRequest.getId()));
        }

        Building building = buildingRepository.findById(updateRequest.getBuildingId())
                .orElseThrow(() -> new BuildingNotFoundException(
                        String.format(BUILDING_RANK_NOT_FOUND, updateRequest.getBuildingId())));

        classroomRepository.save(updateRequestMapper.convertToEntity(updateRequest, building));
    }

    @Override
    @Transactional
    public void deleteById(Integer id) {
        if (!classroomRepository.findById(id).isPresent()) {
            throw new ClassroomNotFoundException(String.format(CLASSROOM_NOT_FOUND, id));
        }

        log.debug("Classroom deleting with id {}", id);

        classroomRepository.deleteById(id);
    }
}
