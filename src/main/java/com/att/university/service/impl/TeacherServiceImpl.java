package com.att.university.service.impl;

import com.att.university.dao.AcademicRankDao;
import com.att.university.dao.ScienceDegreeDao;
import com.att.university.dao.TeacherDao;
import com.att.university.entity.AcademicRank;
import com.att.university.entity.Lesson;
import com.att.university.entity.ScienceDegree;
import com.att.university.entity.Teacher;
import com.att.university.exception.dao.PersonNotFoundException;
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

import java.util.List;

@Component("teacherService")
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TeacherServiceImpl implements TeacherService {
    private static final String TEACHER_NOT_FOUND = "Teacher is not found";

    private final TeacherDao teacherDao;
    private final AcademicRankDao academicRankDao;
    private final ScienceDegreeDao scienceDegreeDao;
    private final TeacherRegisterValidator teacherRegisterValidator;
    private final TeacherUpdateValidator teacherUpdateValidator;
    private final PasswordEncoder passwordEncoder;

    public void register(TeacherRegisterRequest teacherRegisterRequest) {
        teacherRegisterValidator.validate(teacherRegisterRequest);

        log.debug("Teacher update with request {}", teacherRegisterRequest);

        if (teacherDao.findByEmail(teacherRegisterRequest.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        AcademicRank academicRank = academicRankDao.findById(teacherRegisterRequest.getAcademicRankId())
                .orElseThrow(() -> new RuntimeException("Academic Rank does not exists"));

        ScienceDegree scienceDegree = scienceDegreeDao.findById(teacherRegisterRequest.getScienceDegreeId())
                .orElseThrow(() -> new RuntimeException("Science degree does not exists"));

        teacherDao.save(Teacher.builder()
                .withFirstName(teacherRegisterRequest.getFirstName())
                .withLastName(teacherRegisterRequest.getLastName())
                .withEmail(teacherRegisterRequest.getEmail())
                .withPassword(teacherRegisterRequest.getPassword())
                .withLinkedin(teacherRegisterRequest.getLinkedin())
                .withAcademicRank(academicRank)
                .withScienceDegree(scienceDegree)
                .build());
    }

    @Override
    public void update(TeacherUpdateRequest teacherUpdateRequest) {
        teacherUpdateValidator.validate(teacherUpdateRequest);

        log.debug("Teacher update with request {}", teacherUpdateRequest);

        if (!teacherDao.findById(teacherUpdateRequest.getId()).isPresent()) {
            throw new RuntimeException("Teacher is not found");
        }

        AcademicRank academicRank = academicRankDao.findById(teacherUpdateRequest.getAcademicRankId())
                .orElseThrow(() -> new RuntimeException("Academic Rank does not exists"));

        ScienceDegree scienceDegree = scienceDegreeDao.findById(teacherUpdateRequest.getScienceDegreeId())
                .orElseThrow(() -> new RuntimeException("Science degree does not exists"));

        teacherDao.update(Teacher.builder()
                .withId(teacherUpdateRequest.getId())
                .withFirstName(teacherUpdateRequest.getFirstName())
                .withLastName(teacherUpdateRequest.getLastName())
                .withEmail(teacherUpdateRequest.getEmail())
                .withPassword(teacherUpdateRequest.getPassword())
                .withLinkedin(teacherUpdateRequest.getLinkedin())
                .withAcademicRank(academicRank)
                .withScienceDegree(scienceDegree)
                .build());
    }

    @Override
    public boolean login(String email, String password) {
        log.debug("Teacher login with login {}", email);

        Teacher teacher = teacherDao.findByEmail(email)
                .orElseThrow(() -> new RuntimeException(TEACHER_NOT_FOUND));

        return passwordEncoder.matches(password, teacher.getPassword());
    }

    @Override
    public List<Teacher> findAll() {
        return teacherDao.findAll(1, teacherDao.count());
    }

    @Override
    public Teacher findById(Integer id) {
        return teacherDao.findById(id).orElseThrow(() -> new PersonNotFoundException(TEACHER_NOT_FOUND));
    }
}
