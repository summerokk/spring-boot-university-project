package com.att.university.mapper.student;

import com.att.university.entity.Student;
import com.att.university.request.person.student.StudentRegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
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
