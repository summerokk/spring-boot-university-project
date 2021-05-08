package com.att.university.service.impl;

import com.att.university.dao.BuildingDao;
import com.att.university.dao.ClassroomDao;
import com.att.university.entity.Building;
import com.att.university.entity.Classroom;
import com.att.university.exception.dao.BuildingNotFoundException;
import com.att.university.exception.dao.ClassroomNotFoundException;
import com.att.university.mapper.classroom.ClassroomAddRequestMapper;
import com.att.university.mapper.classroom.ClassroomUpdateRequestMapper;
import com.att.university.request.classroom.ClassroomAddRequest;
import com.att.university.request.classroom.ClassroomUpdateRequest;
import com.att.university.service.ClassroomService;
import com.att.university.validator.classroom.ClassroomAddValidator;
import com.att.university.validator.classroom.ClassroomUpdateValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ClassroomServiceImpl implements ClassroomService {
    private static final String CLASSROOM_NOT_FOUND = "Classroom with Id %d is not found";
    private static final String BUILDING_RANK_NOT_FOUND = "Building with Id %d is not found";

    private final ClassroomDao classroomDao;
    private final BuildingDao buildingDao;
    private final ClassroomUpdateValidator updateValidator;
    private final ClassroomAddValidator addValidator;
    private final ClassroomAddRequestMapper addRequestMapper;
    private final ClassroomUpdateRequestMapper updateRequestMapper;

    @Override
    public List<Classroom> findAll() {
        return classroomDao.findAll(1, classroomDao.count());
    }

    @Override
    public Classroom findById(Integer id) {
        return classroomDao.findById(id)
                .orElseThrow(() -> new ClassroomNotFoundException(String.format(CLASSROOM_NOT_FOUND, id)));
    }

    @Override
    public void create(ClassroomAddRequest addRequest) {
        log.debug("Classroom creating with request {}", addRequest);

        addValidator.validate(addRequest);

        Building building = buildingDao.findById(addRequest.getBuildingId())
                .orElseThrow(() -> new BuildingNotFoundException(
                        String.format(BUILDING_RANK_NOT_FOUND, addRequest.getBuildingId())));

        classroomDao.save(addRequestMapper.convertToEntity(addRequest, building));
    }

    @Override
    public void update(ClassroomUpdateRequest updateRequest) {
        updateValidator.validate(updateRequest);

        if (!classroomDao.findById(updateRequest.getId()).isPresent()) {
            throw new ClassroomNotFoundException(String.format(CLASSROOM_NOT_FOUND, updateRequest.getId()));
        }

        Building building = buildingDao.findById(updateRequest.getBuildingId())
                .orElseThrow(() -> new BuildingNotFoundException(
                        String.format(BUILDING_RANK_NOT_FOUND, updateRequest.getBuildingId())));

        classroomDao.update(updateRequestMapper.convertToEntity(updateRequest, building));
    }

    @Override
    public void deleteById(Integer id) {
        if (!classroomDao.findById(id).isPresent()) {
            throw new ClassroomNotFoundException(String.format(CLASSROOM_NOT_FOUND, id));
        }

        log.debug("Classroom deleting with id {}", id);

        classroomDao.deleteById(id);
    }
}
