package com.att.service.impl;

import com.att.exception.dao.FacultyNotFoundException;
import com.att.mapper.faculty.FacultyAddRequestMapper;
import com.att.mapper.faculty.FacultyUpdateRequestMapper;
import com.att.request.faculty.FacultyAddRequest;
import com.att.request.faculty.FacultyUpdateRequest;
import com.att.validator.faculty.FacultyAddValidator;
import com.att.validator.faculty.FacultyUpdateValidator;
import com.att.dao.FacultyRepository;
import com.att.entity.Faculty;
import com.att.service.FacultyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Transactional
public class FacultyServiceImpl implements FacultyService {
    private static final String FACULTY_NOT_FOUND = "Faculty with Id %d is not found";

    private final FacultyRepository facultyRepository;
    private final FacultyUpdateValidator updateValidator;
    private final FacultyAddValidator addValidator;
    private final FacultyAddRequestMapper addRequestMapper;
    private final FacultyUpdateRequestMapper updateRequestMapper;

    @Override
    public List<Faculty> findAll() {
        return facultyRepository.findAll();
    }

    @Override
    public Faculty findById(Integer id) {
        return facultyRepository.findById(id)
                .orElseThrow(() -> new FacultyNotFoundException(String.format(FACULTY_NOT_FOUND, id)));
    }

    @Override
    public void create(FacultyAddRequest addRequest) {
        log.debug("Faculty creating with request {}", addRequest);

        addValidator.validate(addRequest);

        facultyRepository.save(addRequestMapper.convertToEntity(addRequest));
    }

    @Override
    public void update(FacultyUpdateRequest updateRequest) {
        updateValidator.validate(updateRequest);

        if (!facultyRepository.findById(updateRequest.getId()).isPresent()) {
            throw new FacultyNotFoundException(String.format(FACULTY_NOT_FOUND, updateRequest.getId()));
        }

        facultyRepository.save(updateRequestMapper.convertToEntity(updateRequest));
    }

    @Override
    public void deleteById(Integer id) {
        if (!facultyRepository.findById(id).isPresent()) {
            throw new FacultyNotFoundException(String.format(FACULTY_NOT_FOUND, id));
        }

        log.debug("Faculty deleting with id {}", id);

        facultyRepository.deleteById(id);
    }
}
