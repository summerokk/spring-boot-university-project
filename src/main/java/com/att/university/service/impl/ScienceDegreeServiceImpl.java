package com.att.university.service.impl;

import com.att.university.dao.ScienceDegreeDao;
import com.att.university.entity.ScienceDegree;
import com.att.university.exception.dao.ScienceDegreeNotFoundException;
import com.att.university.mapper.science_degree.ScienceDegreeAddRequestMapper;
import com.att.university.mapper.science_degree.ScienceDegreeUpdateRequestMapper;
import com.att.university.request.science_degree.ScienceDegreeAddRequest;
import com.att.university.request.science_degree.ScienceDegreeUpdateRequest;
import com.att.university.service.ScienceDegreeService;
import com.att.university.validator.science_degree.ScienceDegreeAddValidator;
import com.att.university.validator.science_degree.ScienceDegreeUpdateValidator;
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
public class ScienceDegreeServiceImpl implements ScienceDegreeService {
    private static final String SCIENCE_DEGREE_RANK_NOT_FOUND = "Science Degree with Id %d is not found";
    
    private final ScienceDegreeDao scienceDegreeDao;
    private final ScienceDegreeUpdateValidator updateValidator;
    private final ScienceDegreeAddValidator addValidator;
    private final ScienceDegreeAddRequestMapper addRequestMapper;
    private final ScienceDegreeUpdateRequestMapper updateRequestMapper;

    @Override
    public List<ScienceDegree> findAll() {
        return scienceDegreeDao.findAll(1, scienceDegreeDao.count());
    }

    @Override
    public ScienceDegree findById(Integer id) {
        return scienceDegreeDao.findById(id)
                .orElseThrow(() -> new ScienceDegreeNotFoundException(String.format(SCIENCE_DEGREE_RANK_NOT_FOUND, id)));
    }

    @Override
    public void create(ScienceDegreeAddRequest addRequest) {
        log.debug("ScienceDegree creating with request {}", addRequest);

        addValidator.validate(addRequest);

        scienceDegreeDao.save(addRequestMapper.convertToEntity(addRequest));
    }

    @Override
    public void update(ScienceDegreeUpdateRequest updateRequest) {
        updateValidator.validate(updateRequest);

        if (!scienceDegreeDao.findById(updateRequest.getId()).isPresent()) {
            throw new ScienceDegreeNotFoundException(String.format(SCIENCE_DEGREE_RANK_NOT_FOUND, updateRequest.getId()));
        }

        scienceDegreeDao.update(updateRequestMapper.convertToEntity(updateRequest));
    }

    @Override
    public void deleteById(Integer id) {
        if (!scienceDegreeDao.findById(id).isPresent()) {
            throw new ScienceDegreeNotFoundException(String.format(SCIENCE_DEGREE_RANK_NOT_FOUND, id));
        }

        log.debug("ScienceDegree deleting with id {}", id);

        scienceDegreeDao.deleteById(id);
    }
}
