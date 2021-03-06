package com.att.service.impl;

import com.att.dao.GroupRepository;
import com.att.dao.StudentRepository;
import com.att.entity.Group;
import com.att.entity.Student;
import com.att.exception.dao.PersonNotFoundException;
import com.att.exception.service.EmailAlreadyExistsException;
import com.att.exception.service.LoginFailException;
import com.att.mapper.student.StudentRegisterRequestMapper;
import com.att.mapper.student.StudentUpdateRequestMapper;
import com.att.request.person.student.StudentRegisterRequest;
import com.att.request.person.student.StudentUpdateRequest;
import com.att.service.StudentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Component("studentService")
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Transactional
@Validated
public class StudentServiceImpl implements StudentService {
    private static final String STUDENT_NOT_FOUND = "Student is not found";

    private final StudentRepository studentRepository;
    private final GroupRepository groupRepository;
    private final PasswordEncoder passwordEncoder;
    private final StudentRegisterRequestMapper registerRequestMapper;
    private final StudentUpdateRequestMapper updateRequestMapper;

    @Override
    public void register(StudentRegisterRequest studentRegisterRequest) {
        log.debug("Student registration with request {}", studentRegisterRequest);

        if (studentRepository.findByEmail(studentRegisterRequest.getEmail()).isPresent()) {
            throw new EmailAlreadyExistsException("Email already exists");
        }

        studentRepository.save(registerRequestMapper.convertToEntity(studentRegisterRequest,
                passwordEncoder.encode(studentRegisterRequest.getPassword())));
    }

    @Override
    public void update(StudentUpdateRequest studentUpdateRequest) {
        log.debug("Student update with request {}", studentUpdateRequest);

        Student student = studentRepository.findById(studentUpdateRequest.getId())
                .orElseThrow(() -> new PersonNotFoundException(STUDENT_NOT_FOUND));

        Group group = groupRepository.findById(studentUpdateRequest.getGroupId())
                .orElse(null);

        studentRepository.save(updateRequestMapper.convertToEntity(studentUpdateRequest, group, student.getPassword()));
    }

    @Override
    public boolean login(String email, String password) {
        log.debug("Student login with login {}", email);

        Student student = studentRepository.findByEmail(email)
                .orElseThrow(() -> new LoginFailException(STUDENT_NOT_FOUND));

        return passwordEncoder.matches(password, student.getPassword());
    }

    @Override
    public Student findById(Integer id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new PersonNotFoundException(STUDENT_NOT_FOUND));
    }

    @Override
    public Student findByEmail(String email) {
        return studentRepository.findByEmail(email)
                .orElseThrow(() -> new PersonNotFoundException(STUDENT_NOT_FOUND));
    }

    @Override
    public Page<Student> findAll(Pageable pageable) {
        return studentRepository.findAll(pageable);
    }

    @Override
    public void delete(Integer id) {
        if (!studentRepository.findById(id).isPresent()) {
            throw new PersonNotFoundException(STUDENT_NOT_FOUND);
        }

        log.debug("Student deleting with id {}", id);

        studentRepository.deleteById(id);
    }
}
