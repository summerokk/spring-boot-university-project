package com.att.university.mapper.classroom;

import com.att.university.entity.Building;
import com.att.university.entity.Classroom;
import com.att.university.request.classroom.ClassroomAddRequest;
import org.springframework.stereotype.Component;

@Component
public class ClassroomAddRequestMapper {
    public Classroom convertToEntity(ClassroomAddRequest addRequest, Building building) {
        return new Classroom(null, addRequest.getNumber(), building);
    }
}
