package com.att.mapper.classroom;

import com.att.request.classroom.ClassroomAddRequest;
import com.att.entity.Building;
import com.att.entity.Classroom;
import org.springframework.stereotype.Component;

@Component
public class ClassroomAddRequestMapper {
    public Classroom convertToEntity(ClassroomAddRequest addRequest, Building building) {
        return new Classroom(null, addRequest.getNumber(), building);
    }
}
