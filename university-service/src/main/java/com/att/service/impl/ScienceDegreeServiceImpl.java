package com.att.service.impl;

import com.att.exception.dao.ScienceDegreeNotFoundException;
import com.att.mapper.science_degree.ScienceDegreeAddRequestMapper;
import com.att.mapper.science_degree.ScienceDegreeUpdateRequestMapper;
import com.att.request.science_degree.ScienceDegreeAddRequest;
import com.att.request.science_degree.ScienceDegreeUpdateRequest;
import com.att.validator.science_degree.ScienceDegreeAddValidator;
import com.att.validator.science_degree.ScienceDegreeUpdateValidator;
import com.att.dao.ScienceDegreeRepository;
import com.att.entity.ScienceDegree;
import com.att.service.ScienceDegreeService;
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
    
    private final ScienceDegreeRepository scienceDegreeRepository;
    private final ScienceDegreeUpdateValidator updateValidator;
    private final ScienceDegreeAddValidator addValidator;
    private final ScienceDegreeAddRequestMapper addRequestMapper;
    private final ScienceDegreeUpdateRequestMapper updateRequestMapper;

    @Override
    public List<ScienceDegree> findAll() {
        return scienceDegreeRepository.findAll();
    }

    @Override
    public ScienceDegree findById(Integer id) {
        return scienceDegreeRepository.findById(id)
                .orElseThrow(() -> new ScienceDegreeNotFoundException(String.format(SCIENCE_DEGREE_RANK_NOT_FOUND, id)));
    }

    @Override
    public void create(ScienceDegreeAddRequest addRequest) {
        log.debug("ScienceDegree creating with request {}", addRequest);

        addValidator.validate(addRequest);

        scienceDegreeRepository.save(addRequestMapper.convertToEntity(addRequest));
    }

    @Override
    public void update(ScienceDegreeUpdateRequest updateRequest) {
        updateValidator.validate(updateRequest);

        if (!scienceDegreeRepository.findById(updateRequest.getId()).isPresent()) {
            throw new ScienceDegreeNotFoundException(String.format(SCIENCE_DEGREE_RANK_NOT_FOUND, updateRequest.getId()));
        }

        scienceDegreeRepository.save(updateRequestMapper.convertToEntity(updateRequest));
    }

    @Override
    public void deleteById(Integer id) {
        if (!scienceDegreeRepository.findById(id).isPresent()) {
            throw new ScienceDegreeNotFoundException(String.format(SCIENCE_DEGREE_RANK_NOT_FOUND, id));
        }

        log.debug("ScienceDegree deleting with id {}", id);

        scienceDegreeRepository.deleteById(id);
    }
}
