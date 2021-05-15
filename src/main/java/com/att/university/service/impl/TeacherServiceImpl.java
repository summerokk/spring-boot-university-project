package com.att.university.service.impl;

import com.att.university.dao.AcademicRankDao;
import com.att.university.dao.ScienceDegreeDao;
import com.att.university.dao.TeacherDao;
import com.att.university.entity.AcademicRank;
import com.att.university.entity.ScienceDegree;
import com.att.university.entity.Teacher;
import com.att.university.exception.dao.PersonNotFoundException;
import com.att.university.exception.service.EmailAlreadyExistsException;
import com.att.university.mapper.teacher.TeacherRegisterRequestMapper;
import com.att.university.mapper.teacher.TeacherUpdateRequestMapper;
import com.att.university.request.person.teacher.TeacherRegisterRequest;
import com.att.university.request.person.teacher.TeacherUpdateRequest;
import com.att.university.service.TeacherService;

import com.att.university.validator.person.TeacherRegisterValidator;
import com.att.university.validator.person.TeacherUpdateValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component("teacherService")
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Transactional
public class TeacherServiceImpl implements TeacherService {
    private static final String TEACHER_NOT_FOUND = "Teacher with Id %d is not found";
    private static final String TEACHER_NOT_FOUND_WITH_EMAIL = "Teacher with email %s is not found";

    private final TeacherDao teacherDao;
    private final AcademicRankDao academicRankDao;
    private final ScienceDegreeDao scienceDegreeDao;
    private final TeacherRegisterValidator teacherRegisterValidator;
    private final TeacherUpdateValidator teacherUpdateValidator;
    private final TeacherRegisterRequestMapper teacherRegisterRequestMapper;
    private final TeacherUpdateRequestMapper teacherUpdateRequestMapper;
    private final PasswordEncoder passwordEncoder;

    public void register(TeacherRegisterRequest teacherRegisterRequest) {
        teacherRegisterValidator.validate(teacherRegisterRequest);

        log.debug("Teacher register with request {}", teacherRegisterRequest);

        if (teacherDao.findByEmail(teacherRegisterRequest.getEmail()).isPresent()) {
            throw new EmailAlreadyExistsException("Email already exists");
        }

        AcademicRank academicRank = academicRankDao.findById(teacherRegisterRequest.getAcademicRankId())
                .orElseThrow(() -> new RuntimeException("Academic Rank does not exists"));

        ScienceDegree scienceDegree = scienceDegreeDao.findById(teacherRegisterRequest.getScienceDegreeId())
                .orElseThrow(() -> new RuntimeException("Science degree does not exists"));

        teacherDao.save(teacherRegisterRequestMapper.convertToEntity(teacherRegisterRequest,
                passwordEncoder.encode(teacherRegisterRequest.getPassword()), academicRank, scienceDegree));
    }

    @Override
    public void update(TeacherUpdateRequest teacherUpdateRequest) {
        teacherUpdateValidator.validate(teacherUpdateRequest);

        log.debug("Teacher update with request {}", teacherUpdateRequest);

        if (!teacherDao.findById(teacherUpdateRequest.getId()).isPresent()) {
            throw new PersonNotFoundException(String.format(TEACHER_NOT_FOUND, teacherUpdateRequest.getId()));
        }

        AcademicRank academicRank = academicRankDao.findById(teacherUpdateRequest.getAcademicRankId())
                .orElseThrow(() -> new RuntimeException("Academic Rank does not exists"));

        ScienceDegree scienceDegree = scienceDegreeDao.findById(teacherUpdateRequest.getScienceDegreeId())
                .orElseThrow(() -> new RuntimeException("Science degree does not exists"));

        teacherDao.update(teacherUpdateRequestMapper.convertToEntity(teacherUpdateRequest, academicRank, scienceDegree));
    }

    @Override
    public boolean login(String email, String password) {
        log.debug("Teacher login with login {}", email);

        Teacher teacher = teacherDao.findByEmail(email)
                .orElseThrow(() -> new RuntimeException(TEACHER_NOT_FOUND));

        return passwordEncoder.matches(password, teacher.getPassword());
    }

    @Override
    public List<Teacher> findAll(int page, int count) {
        return teacherDao.findAll(page, count);
    }

    @Override
    public List<Teacher> findAll() {
        return teacherDao.findAll();
    }

    @Override
    public Teacher findById(Integer id) {
        return teacherDao.findById(id).orElseThrow(() ->
                new PersonNotFoundException(String.format(TEACHER_NOT_FOUND, id)));
    }

    @Override
    public Teacher findByEmail(String email) {
        return teacherDao.findByEmail(email)
                .orElseThrow(() -> new PersonNotFoundException(String.format(TEACHER_NOT_FOUND_WITH_EMAIL, email)));
    }

    @Override
    public int count() {
        return teacherDao.count();
    }

    @Override
    public void deleteById(Integer id) {
        if (!teacherDao.findById(id).isPresent()) {
            throw new PersonNotFoundException(String.format(TEACHER_NOT_FOUND, id));
        }

        teacherDao.deleteById(id);
    }
}
