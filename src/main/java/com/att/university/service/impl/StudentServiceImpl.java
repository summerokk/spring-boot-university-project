package com.att.university.service.impl;

import com.att.university.dao.GroupDao;
import com.att.university.dao.StudentDao;
import com.att.university.entity.Group;
import com.att.university.entity.Student;
import com.att.university.exception.dao.GroupNotFoundException;
import com.att.university.exception.dao.PersonNotFoundException;
import com.att.university.exception.service.EmailAlreadyExistsException;
import com.att.university.exception.service.LoginFailException;
import com.att.university.mapper.student.StudentRegisterRequestMapper;
import com.att.university.mapper.student.StudentUpdateRequestMapper;
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

import java.util.List;

@Component("studentService")
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class StudentServiceImpl implements StudentService {
    private static final String STUDENT_NOT_FOUND = "Student is not found";

    private final StudentDao studentDao;
    private final GroupDao groupDao;
    private final StudentRegisterValidator studentRegisterValidator;
    private final StudentUpdateValidator studentUpdateValidator;
    private final PasswordEncoder passwordEncoder;
    private final StudentRegisterRequestMapper registerRequestMapper;
    private final StudentUpdateRequestMapper updateRequestMapper;

    public void register(StudentRegisterRequest studentRegisterRequest) {
        studentRegisterValidator.validate(studentRegisterRequest);

        log.debug("Student registration with request {}", studentRegisterRequest);

        if (studentDao.findByEmail(studentRegisterRequest.getEmail()).isPresent()) {
            throw new EmailAlreadyExistsException("Email already exists");
        }

        studentDao.save(registerRequestMapper.convertToEntity(studentRegisterRequest,
                passwordEncoder.encode(studentRegisterRequest.getPassword())));
    }

    @Override
    public void update(StudentUpdateRequest studentUpdateRequest) {
        studentUpdateValidator.validate(studentUpdateRequest);

        log.debug("Student update with request {}", studentUpdateRequest);

        if(!studentDao.findById(studentUpdateRequest.getId()).isPresent()) {
            throw new PersonNotFoundException(STUDENT_NOT_FOUND);
        }

        Group group = groupDao.findById(studentUpdateRequest.getGroupId())
                .orElseThrow(() -> new GroupNotFoundException("Group is not found"));

        studentDao.update(updateRequestMapper.convertToEntity(studentUpdateRequest, group));
    }

    @Override
    public boolean login(String email, String password) {
        log.debug("Student login with login {}", email);

        Student student = studentDao.findByEmail(email)
                .orElseThrow(() -> new LoginFailException(STUDENT_NOT_FOUND));

        return passwordEncoder.matches(password, student.getPassword());
    }

    @Override
    public Student findById(Integer id) {
        return studentDao.findById(id)
                .orElseThrow(() -> new PersonNotFoundException(STUDENT_NOT_FOUND));
    }

    @Override
    public Student findByEmail(String email) {
        return studentDao.findByEmail(email)
                .orElseThrow(() -> new PersonNotFoundException(STUDENT_NOT_FOUND));
    }

    @Override
    public List<Student> findAll(int page, int count) {
        return studentDao.findAll(page, count);
    }

    @Override
    public int count() {
        return studentDao.count();
    }

    @Override
    public void delete(Integer id) {
        if (!studentDao.findById(id).isPresent()) {
            throw new PersonNotFoundException(STUDENT_NOT_FOUND);
        }

        log.debug("Student deleting with id {}", id);

        studentDao.deleteById(id);
    }
}
