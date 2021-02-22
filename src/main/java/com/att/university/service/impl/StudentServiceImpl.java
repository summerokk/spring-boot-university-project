package com.att.university.service.impl;

import com.att.university.dao.GroupDao;
import com.att.university.dao.StudentDao;
import com.att.university.entity.Group;
import com.att.university.entity.Student;
import com.att.university.service.StudentService;
import com.att.university.validator.StudentValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component("studentService")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class StudentServiceImpl implements StudentService {
    private final StudentDao studentDao;
    private final GroupDao groupDao;
    private final StudentValidator studentValidator;
    private final PasswordEncoder passwordEncoder;

    public void save(Student student, Integer groupId) {
        studentValidator.validate(student);


        if (studentDao.findByEmail(student.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        Group group = groupDao.findById(groupId).orElseThrow(() -> new RuntimeException("Group is not found"));

        studentDao.save(student.toBuilder()
                .withPassword(passwordEncoder.encode(student.getPassword()))
                .withGroup(group)
                .build());
    }
}
