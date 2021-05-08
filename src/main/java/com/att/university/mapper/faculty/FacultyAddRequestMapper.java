package com.att.university.mapper.faculty;

import com.att.university.entity.Faculty;
import com.att.university.request.faculty.FacultyAddRequest;
import org.springframework.stereotype.Component;

@Component
public class FacultyAddRequestMapper {
    public Faculty convertToEntity(FacultyAddRequest addRequest) {
        return new Faculty(null, addRequest.getName());
    }
}
