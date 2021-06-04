package com.att.mapper.classroom;

import com.att.request.classroom.ClassroomUpdateRequest;
import com.att.entity.Building;
import com.att.entity.Classroom;
import org.springframework.stereotype.Component;

@Component
public class ClassroomUpdateRequestMapper {
    public Classroom convertToEntity(ClassroomUpdateRequest updateRequest, Building building) {
        return new Classroom(updateRequest.getId(), updateRequest.getNumber(), building);
    }
}
