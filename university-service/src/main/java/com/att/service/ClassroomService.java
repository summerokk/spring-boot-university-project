package com.att.service;

import com.att.request.classroom.ClassroomAddRequest;
import com.att.request.classroom.ClassroomUpdateRequest;
import com.att.entity.Classroom;

import javax.validation.Valid;
import java.util.List;


public interface ClassroomService {
    List<Classroom> findAll();

    Classroom findById(Integer id);

    void update(@Valid ClassroomUpdateRequest updateRequest);

    void create(@Valid ClassroomAddRequest addRequest);

    void deleteById(Integer id);
}
