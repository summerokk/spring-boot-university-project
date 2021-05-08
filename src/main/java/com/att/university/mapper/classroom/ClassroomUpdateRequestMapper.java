package com.att.university.mapper.classroom;

import com.att.university.entity.Building;
import com.att.university.entity.Classroom;
import com.att.university.request.classroom.ClassroomUpdateRequest;
import org.springframework.stereotype.Component;

@Component
public class ClassroomUpdateRequestMapper {
    public Classroom convertToEntity(ClassroomUpdateRequest updateRequest, Building building) {
        return new Classroom(updateRequest.getId(), updateRequest.getNumber(), building);
    }
}
