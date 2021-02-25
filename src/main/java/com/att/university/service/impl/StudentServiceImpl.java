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

    public void register(Student student) {
        studentValidator.validate(student);

        if (studentDao.findByEmail(student.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        studentDao.save(student);
    }

    @Override
    public void update(Student student) {
        studentValidator.validate(student);

        if(!studentDao.findById(student.getId()).isPresent()) {
            throw new RuntimeException("Student is not found");
        }

        studentDao.update(student);
    }

    @Override
    public boolean login(String email, String password) {
        Student student = studentDao.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Student is not found"));

        return student.getPassword().equals(passwordEncoder.encode(password));
    }
}
