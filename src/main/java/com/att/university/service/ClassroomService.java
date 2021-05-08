package com.att.university.service;

import com.att.university.entity.Classroom;
import com.att.university.request.classroom.ClassroomAddRequest;
import com.att.university.request.classroom.ClassroomUpdateRequest;

import java.util.List;


public interface ClassroomService {
    List<Classroom> findAll();

    Classroom findById(Integer id);

    void update(ClassroomUpdateRequest updateRequest);

    void create(ClassroomAddRequest addRequest);

    void deleteById(Integer id);
}
