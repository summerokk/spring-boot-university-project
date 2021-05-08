package com.att.university.service.impl;

import com.att.university.dao.CourseDao;
import com.att.university.entity.Course;
import com.att.university.exception.dao.CourseNotFoundException;
import com.att.university.mapper.course.CourseAddRequestMapper;
import com.att.university.mapper.course.CourseUpdateRequestMapper;
import com.att.university.request.course.CourseAddRequest;
import com.att.university.request.course.CourseUpdateRequest;
import com.att.university.service.CourseService;
import com.att.university.validator.course.CourseAddValidator;
import com.att.university.validator.course.CourseUpdateValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CourseServiceImpl implements CourseService {
    private static final String COURSE_NOT_FOUND = "Course with Id %d is not found";

    private final CourseDao courseDao;
    private final CourseUpdateValidator updateValidator;
    private final CourseAddValidator addValidator;
    private final CourseAddRequestMapper addRequestMapper;
    private final CourseUpdateRequestMapper updateRequestMapper;

    @Override
    public List<Course> findAll() {
        return courseDao.findAll(1, courseDao.count());
    }

    @Override
    public Course findById(Integer id) {
        return courseDao.findById(id)
                .orElseThrow(() -> new CourseNotFoundException(String.format(COURSE_NOT_FOUND, id)));
    }

    @Override
    public void create(CourseAddRequest addRequest) {
        log.debug("Course creating with request {}", addRequest);

        addValidator.validate(addRequest);

        courseDao.save(addRequestMapper.convertToEntity(addRequest));
    }

    @Override
    public void update(CourseUpdateRequest updateRequest) {
        updateValidator.validate(updateRequest);

        if (!courseDao.findById(updateRequest.getId()).isPresent()) {
            throw new CourseNotFoundException(String.format(COURSE_NOT_FOUND, updateRequest.getId()));
        }

        courseDao.update(updateRequestMapper.convertToEntity(updateRequest));
    }

    @Override
    public void deleteById(Integer id) {
        if (!courseDao.findById(id).isPresent()) {
            throw new CourseNotFoundException(String.format(COURSE_NOT_FOUND, id));
        }

        log.debug("Course deleting with id {}", id);

        courseDao.deleteById(id);
    }
}
