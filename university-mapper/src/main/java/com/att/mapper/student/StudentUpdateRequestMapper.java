package com.att.mapper.student;

import com.att.request.person.student.StudentUpdateRequest;
import com.att.entity.Group;
import com.att.entity.Student;
import org.springframework.stereotype.Component;

@Component
public class StudentUpdateRequestMapper {
    public Student convertToEntity(StudentUpdateRequest updateRequest, Group group, String password) {
        return Student.builder()
                .withId(updateRequest.getId())
                .withFirstName(updateRequest.getFirstName())
                .withLastName(updateRequest.getLastName())
                .withEmail(updateRequest.getEmail())
                .withPassword(password)
                .withGroup(group)
                .build();
    }
}
