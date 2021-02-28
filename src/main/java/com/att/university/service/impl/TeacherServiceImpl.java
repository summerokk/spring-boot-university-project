package com.att.university.service.impl;

import com.att.university.dao.AcademicRankDao;
import com.att.university.dao.ScienceDegreeDao;
import com.att.university.dao.TeacherDao;
import com.att.university.entity.AcademicRank;
import com.att.university.entity.ScienceDegree;
import com.att.university.entity.Teacher;
import com.att.university.request.person.teacher.TeacherRegisterRequest;
import com.att.university.request.person.teacher.TeacherUpdateRequest;
import com.att.university.service.TeacherService;

import com.att.university.validator.person.TeacherRegisterValidator;
import com.att.university.validator.person.TeacherUpdateValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component("teacherService")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TeacherServiceImpl implements TeacherService {
    private final TeacherDao teacherDao;
    private final AcademicRankDao academicRankDao;
    private final ScienceDegreeDao scienceDegreeDao;
    private final TeacherRegisterValidator teacherRegisterValidator;
    private final TeacherUpdateValidator teacherUpdateValidator;
    private final PasswordEncoder passwordEncoder;

    public void register(TeacherRegisterRequest teacher) {
        teacherRegisterValidator.validate(teacher);

        if (teacherDao.findByEmail(teacher.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        AcademicRank academicRank = academicRankDao.findById(teacher.getAcademicRankId())
                .orElseThrow(() -> new RuntimeException("Academic Rank does not exists"));

        ScienceDegree scienceDegree = scienceDegreeDao.findById(teacher.getScienceDegreeId())
                .orElseThrow(() -> new RuntimeException("Science degree does not exists"));

        teacherDao.save(Teacher.builder()
                .withFirstName(teacher.getFirstName())
                .withLastName(teacher.getLastName())
                .withEmail(teacher.getEmail())
                .withPassword(teacher.getPassword())
                .withLinkedin(teacher.getLinkedin())
                .withAcademicRank(academicRank)
                .withScienceDegree(scienceDegree)
                .build());
    }

    @Override
    public void update(TeacherUpdateRequest teacher) {
        teacherUpdateValidator.validate(teacher);

        if (!teacherDao.findById(teacher.getId()).isPresent()) {
            throw new RuntimeException("Teacher is not found");
        }

        AcademicRank academicRank = academicRankDao.findById(teacher.getAcademicRankId())
                .orElseThrow(() -> new RuntimeException("Academic Rank does not exists"));

        ScienceDegree scienceDegree = scienceDegreeDao.findById(teacher.getScienceDegreeId())
                .orElseThrow(() -> new RuntimeException("Science degree does not exists"));

        teacherDao.update(Teacher.builder()
                .withId(teacher.getId())
                .withFirstName(teacher.getFirstName())
                .withLastName(teacher.getLastName())
                .withEmail(teacher.getEmail())
                .withPassword(teacher.getPassword())
                .withLinkedin(teacher.getLinkedin())
                .withAcademicRank(academicRank)
                .withScienceDegree(scienceDegree)
                .build());
    }

    @Override
    public boolean login(String email, String password) {
        Teacher teacher = teacherDao.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("teacher is not found"));

        return teacher.getPassword().equals(passwordEncoder.encode(password));
    }
}
