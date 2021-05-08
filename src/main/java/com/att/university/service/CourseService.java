package com.att.university.service;

import com.att.university.entity.Course;
import com.att.university.request.course.CourseAddRequest;
import com.att.university.request.course.CourseUpdateRequest;
import com.att.university.request.person.teacher.TeacherUpdateRequest;

import java.util.List;

public interface CourseService {
    List<Course> findAll();

    Course findById(Integer id);

    void update(CourseUpdateRequest updateRequest);

    void create(CourseAddRequest addRequest);

    void deleteById(Integer id);
}
