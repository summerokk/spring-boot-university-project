package com.att.university.service.impl;

import com.att.university.dao.AcademicRankDao;
import com.att.university.entity.AcademicRank;
import com.att.university.exception.dao.AcademicRankNotFoundException;
import com.att.university.mapper.academic_rank.AcademicRankAddRequestMapper;
import com.att.university.mapper.academic_rank.AcademicRankUpdateRequestMapper;
import com.att.university.request.academic_rank.AcademicRankAddRequest;
import com.att.university.request.academic_rank.AcademicRankUpdateRequest;
import com.att.university.service.AcademicRankService;
import com.att.university.validator.academic_rank.AcademicRankAddValidator;
import com.att.university.validator.academic_rank.AcademicRankUpdateValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AcademicRankServiceImpl implements AcademicRankService {
    private static final String ACADEMIC_RANK_NOT_FOUND = "Academic rank with Id %d is not found";

    private final AcademicRankDao academicRankDao;
    private final AcademicRankUpdateValidator updateValidator;
    private final AcademicRankAddValidator addValidator;
    private final AcademicRankAddRequestMapper addRequestMapper;
    private final AcademicRankUpdateRequestMapper updateRequestMapper;

    @Override
    public List<AcademicRank> findAll() {
        log.debug("Find all academic ranks...");

        return academicRankDao.findAll(1, academicRankDao.count());
    }

    @Override
    public AcademicRank findById(Integer id) {
        return academicRankDao.findById(id)
                .orElseThrow(() -> new AcademicRankNotFoundException(String.format(ACADEMIC_RANK_NOT_FOUND, id)));
    }

    @Override
    @Transactional
    public void create(AcademicRankAddRequest addRequest) {
        log.debug("Academic rank creating with request {}", addRequest);

        addValidator.validate(addRequest);

        academicRankDao.save(addRequestMapper.convertToEntity(addRequest));
    }

    @Override
    @Transactional
    public void update(AcademicRankUpdateRequest updateRequest) {
        updateValidator.validate(updateRequest);

        if (!academicRankDao.findById(updateRequest.getId()).isPresent()) {
            throw new AcademicRankNotFoundException(String.format(ACADEMIC_RANK_NOT_FOUND, updateRequest.getId()));
        }

        academicRankDao.update(updateRequestMapper.convertToEntity(updateRequest));
    }

    @Override
    @Transactional
    public void deleteById(Integer id) {
        if (!academicRankDao.findById(id).isPresent()) {
            throw new AcademicRankNotFoundException(String.format(ACADEMIC_RANK_NOT_FOUND, id));
        }

        log.debug("Academic rank deleting with id {}", id);

        academicRankDao.deleteById(id);
    }
}
