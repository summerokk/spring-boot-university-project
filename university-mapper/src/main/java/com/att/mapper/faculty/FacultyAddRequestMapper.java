package com.att.mapper.faculty;

import com.att.request.faculty.FacultyAddRequest;
import com.att.entity.Faculty;
import org.springframework.stereotype.Component;

@Component
public class FacultyAddRequestMapper {
    public Faculty convertToEntity(FacultyAddRequest addRequest) {
        return new Faculty(null, addRequest.getName());
    }
}
