package com.att.university.service.impl;

import com.att.university.dao.FacultyDao;
import com.att.university.entity.Faculty;
import com.att.university.exception.dao.FacultyNotFoundException;
import com.att.university.exception.dao.GroupNotFoundException;
import com.att.university.mapper.faculty.FacultyAddRequestMapper;
import com.att.university.mapper.faculty.FacultyUpdateRequestMapper;
import com.att.university.request.faculty.FacultyAddRequest;
import com.att.university.request.faculty.FacultyUpdateRequest;
import com.att.university.service.FacultyService;
import com.att.university.validator.faculty.FacultyAddValidator;
import com.att.university.validator.faculty.FacultyUpdateValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class FacultyServiceImpl implements FacultyService {
    private static final String FACULTY_NOT_FOUND = "Faculty with Id %d is not found";

    private final FacultyDao facultyDao;
    private final FacultyUpdateValidator updateValidator;
    private final FacultyAddValidator addValidator;
    private final FacultyAddRequestMapper addRequestMapper;
    private final FacultyUpdateRequestMapper updateRequestMapper;

    @Override
    public List<Faculty> findAll() {
        return facultyDao.findAll(1, facultyDao.count());
    }

    @Override
    public Faculty findById(Integer id) {
        return facultyDao.findById(id)
                .orElseThrow(() -> new FacultyNotFoundException(String.format(FACULTY_NOT_FOUND, id)));
    }

    @Override
    public void create(FacultyAddRequest addRequest) {
        log.debug("Faculty creating with request {}", addRequest);

        addValidator.validate(addRequest);

        facultyDao.save(addRequestMapper.convertToEntity(addRequest));
    }

    @Override
    public void update(FacultyUpdateRequest updateRequest) {
        updateValidator.validate(updateRequest);

        if (!facultyDao.findById(updateRequest.getId()).isPresent()) {
            throw new FacultyNotFoundException(String.format(FACULTY_NOT_FOUND, updateRequest.getId()));
        }

        facultyDao.update(updateRequestMapper.convertToEntity(updateRequest));
    }

    @Override
    public void deleteById(Integer id) {
        if (!facultyDao.findById(id).isPresent()) {
            throw new FacultyNotFoundException(String.format(FACULTY_NOT_FOUND, id));
        }

        log.debug("Faculty deleting with id {}", id);

        facultyDao.deleteById(id);
    }
}
