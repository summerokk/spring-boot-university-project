package com.att.university.mapper.faculty;

import com.att.university.entity.Faculty;
import com.att.university.request.faculty.FacultyUpdateRequest;
import org.springframework.stereotype.Component;

@Component
public class FacultyUpdateRequestMapper {
    public Faculty convertToEntity(FacultyUpdateRequest updateRequest) {
        return new Faculty(updateRequest.getId(), updateRequest.getName());
    }
}
