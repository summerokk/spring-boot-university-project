package com.att.mapper.faculty;

import com.att.request.faculty.FacultyUpdateRequest;
import com.att.entity.Faculty;
import org.springframework.stereotype.Component;

@Component
public class FacultyUpdateRequestMapper {
    public Faculty convertToEntity(FacultyUpdateRequest updateRequest) {
        return new Faculty(updateRequest.getId(), updateRequest.getName());
    }
}
