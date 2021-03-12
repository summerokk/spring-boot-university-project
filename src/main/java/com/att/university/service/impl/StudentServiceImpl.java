package com.att.university.service.impl;

import com.att.university.dao.GroupDao;
import com.att.university.dao.StudentDao;
import com.att.university.entity.Group;
import com.att.university.entity.Student;
import com.att.university.request.person.student.StudentRegisterRequest;
import com.att.university.request.person.student.StudentUpdateRequest;
import com.att.university.service.StudentService;
import com.att.university.validator.person.StudentRegisterValidator;
import com.att.university.validator.person.StudentUpdateValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component("studentService")
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class StudentServiceImpl implements StudentService {
    private final StudentDao studentDao;
    private final GroupDao groupDao;
    private final StudentRegisterValidator studentRegisterValidator;
    private final StudentUpdateValidator studentUpdateValidator;
    private final PasswordEncoder passwordEncoder;

    public void register(StudentRegisterRequest studentRegisterRequest) {
        studentRegisterValidator.validate(studentRegisterRequest);

        log.debug("Student registration with request {}", studentRegisterRequest);

        if (studentDao.findByEmail(studentRegisterRequest.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        studentDao.save(Student.builder()
                .withFirstName(studentRegisterRequest.getFirstName())
                .withLastName(studentRegisterRequest.getLastName())
                .withEmail(studentRegisterRequest.getEmail())
                .withPassword(studentRegisterRequest.getPassword())
                .build());
    }

    @Override
    public void update(StudentUpdateRequest studentUpdateRequest) {
        studentUpdateValidator.validate(studentUpdateRequest);

        log.debug("Student update with request {}", studentUpdateRequest);


        if(!studentDao.findById(studentUpdateRequest.getId()).isPresent()) {
            throw new RuntimeException("Student is not found");
        }

        Group group = groupDao.findById(studentUpdateRequest.getGroupId()).orElse(null);

        studentDao.update(Student.builder()
                .withId(studentUpdateRequest.getId())
                .withFirstName(studentUpdateRequest.getFirstName())
                .withLastName(studentUpdateRequest.getLastName())
                .withEmail(studentUpdateRequest.getEmail())
                .withPassword(studentUpdateRequest.getPassword())
                .withGroup(group)
                .build());
    }

    @Override
    public boolean login(String email, String password) {
        log.debug("Student login with login {}", email);

        Student student = studentDao.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Student is not found"));

        return student.getPassword().equals(passwordEncoder.encode(password));
    }
}
