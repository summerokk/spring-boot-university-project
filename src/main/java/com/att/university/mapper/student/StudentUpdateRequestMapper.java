package com.att.university.mapper.student;

import com.att.university.entity.Group;
import com.att.university.entity.Student;
import com.att.university.request.person.student.StudentUpdateRequest;
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
