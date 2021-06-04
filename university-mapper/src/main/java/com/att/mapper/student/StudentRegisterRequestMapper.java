package com.att.mapper.student;

import com.att.request.person.student.StudentRegisterRequest;
import com.att.entity.Student;
import org.springframework.stereotype.Component;

@Component
public class StudentRegisterRequestMapper {
    public Student convertToEntity(StudentRegisterRequest registerRequest, String password) {
        return Student.builder()
                .withFirstName(registerRequest.getFirstName())
                .withLastName(registerRequest.getLastName())
                .withEmail(registerRequest.getEmail())
                .withPassword(password)
                .build();
    }
}
