package com.att.service.impl;

import com.att.dao.AcademicRankRepository;
import com.att.dao.ScienceDegreeRepository;
import com.att.dao.TeacherRepository;
import com.att.entity.AcademicRank;
import com.att.entity.ScienceDegree;
import com.att.entity.Teacher;
import com.att.exception.dao.PersonNotFoundException;
import com.att.exception.service.EmailAlreadyExistsException;
import com.att.mapper.teacher.TeacherRegisterRequestMapper;
import com.att.mapper.teacher.TeacherUpdateRequestMapper;
import com.att.request.person.teacher.TeacherRegisterRequest;
import com.att.request.person.teacher.TeacherUpdateRequest;
import com.att.service.TeacherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Component("teacherService")
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Transactional
@Validated
public class TeacherServiceImpl implements TeacherService {
    private static final String TEACHER_NOT_FOUND = "Teacher with Id %d is not found";
    private static final String TEACHER_NOT_FOUND_WITH_EMAIL = "Teacher with email %s is not found";

    private final TeacherRepository teacherRepository;
    private final AcademicRankRepository academicRankRepository;
    private final ScienceDegreeRepository scienceDegreeRepository;
    private final TeacherRegisterRequestMapper teacherRegisterRequestMapper;
    private final TeacherUpdateRequestMapper teacherUpdateRequestMapper;
    private final PasswordEncoder passwordEncoder;

    public void register(TeacherRegisterRequest teacherRegisterRequest) {
        log.debug("Teacher register with request {}", teacherRegisterRequest);

        if (teacherRepository.findByEmail(teacherRegisterRequest.getEmail()).isPresent()) {
            throw new EmailAlreadyExistsException("Email already exists");
        }

        AcademicRank academicRank = academicRankRepository.findById(teacherRegisterRequest.getAcademicRankId())
                .orElseThrow(() -> new RuntimeException("Academic Rank does not exists"));

        ScienceDegree scienceDegree = scienceDegreeRepository.findById(teacherRegisterRequest.getScienceDegreeId())
                .orElseThrow(() -> new RuntimeException("Science degree does not exists"));

        teacherRepository.save(teacherRegisterRequestMapper.convertToEntity(teacherRegisterRequest,
                passwordEncoder.encode(teacherRegisterRequest.getPassword()), academicRank, scienceDegree));
    }

    @Override
    public void update(TeacherUpdateRequest teacherUpdateRequest) {
        log.debug("Teacher update with request {}", teacherUpdateRequest);

        if (!teacherRepository.findById(teacherUpdateRequest.getId()).isPresent()) {
            throw new PersonNotFoundException(String.format(TEACHER_NOT_FOUND, teacherUpdateRequest.getId()));
        }

        AcademicRank academicRank = academicRankRepository.findById(teacherUpdateRequest.getAcademicRankId())
                .orElseThrow(() -> new RuntimeException("Academic Rank does not exists"));

        ScienceDegree scienceDegree = scienceDegreeRepository.findById(teacherUpdateRequest.getScienceDegreeId())
                .orElseThrow(() -> new RuntimeException("Science degree does not exists"));

        teacherRepository.save(teacherUpdateRequestMapper.convertToEntity(teacherUpdateRequest, academicRank, scienceDegree));
    }

    @Override
    public boolean login(String email, String password) {
        log.debug("Teacher login with login {}", email);

        Teacher teacher = teacherRepository.findByEmail(email)
                .orElseThrow(() -> new PersonNotFoundException(TEACHER_NOT_FOUND));

        return passwordEncoder.matches(password, teacher.getPassword());
    }

    @Override
    public List<Teacher> findAll() {
        return teacherRepository.findAll();
    }

    @Override
    public Page<Teacher> findAll(Pageable pageable) {
        return teacherRepository.findAll(pageable);
    }

    @Override
    public Teacher findById(Integer id) {
        return teacherRepository.findById(id).orElseThrow(() ->
                new PersonNotFoundException(String.format(TEACHER_NOT_FOUND, id)));
    }

    @Override
    public Teacher findByEmail(String email) {
        return teacherRepository.findByEmail(email)
                .orElseThrow(() -> new PersonNotFoundException(String.format(TEACHER_NOT_FOUND_WITH_EMAIL, email)));
    }

    @Override
    public void deleteById(Integer id) {
        if (!teacherRepository.findById(id).isPresent()) {
            throw new PersonNotFoundException(String.format(TEACHER_NOT_FOUND, id));
        }

        teacherRepository.deleteById(id);
    }
}
