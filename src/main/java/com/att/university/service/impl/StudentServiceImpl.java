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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component("studentService")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class StudentServiceImpl implements StudentService {
    private final StudentDao studentDao;
    private final GroupDao groupDao;
    private final StudentRegisterValidator studentRegisterValidator;
    private final StudentUpdateValidator studentUpdateValidator;
    private final PasswordEncoder passwordEncoder;

    public void register(StudentRegisterRequest student) {
        studentRegisterValidator.validate(student);

        if (studentDao.findByEmail(student.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        studentDao.save(Student.builder()
                .withFirstName(student.getFirstName())
                .withLastName(student.getLastName())
                .withEmail(student.getEmail())
                .withPassword(student.getPassword())
                .build());
    }

    @Override
    public void update(StudentUpdateRequest student) {
        studentUpdateValidator.validate(student);

        if(!studentDao.findById(student.getId()).isPresent()) {
            throw new RuntimeException("Student is not found");
        }

        Group group = groupDao.findById(student.getGroupId()).orElse(null);

        studentDao.update(Student.builder()
                .withId(student.getId())
                .withFirstName(student.getFirstName())
                .withLastName(student.getLastName())
                .withEmail(student.getEmail())
                .withPassword(student.getPassword())
                .withGroup(group)
                .build());
    }

    @Override
    public boolean login(String email, String password) {
        Student student = studentDao.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Student is not found"));

        return student.getPassword().equals(passwordEncoder.encode(password));
    }
}
