package com.att.service;

import com.att.request.course.CourseAddRequest;
import com.att.request.course.CourseUpdateRequest;
import com.att.entity.Course;

import javax.validation.Valid;
import java.util.List;

public interface CourseService {
    List<Course> findAll();

    Course findById(Integer id);

    void update(@Valid CourseUpdateRequest updateRequest);

    void create(@Valid CourseAddRequest addRequest);

    void deleteById(Integer id);
}
