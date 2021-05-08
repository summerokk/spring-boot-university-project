package com.att.university.service.impl;

import com.att.university.dao.FacultyDao;
import com.att.university.dao.GroupDao;
import com.att.university.entity.Faculty;
import com.att.university.entity.Group;
import com.att.university.exception.dao.FacultyNotFoundException;
import com.att.university.exception.dao.GroupNotFoundException;
import com.att.university.mapper.group.GroupAddRequestMapper;
import com.att.university.mapper.group.GroupUpdateRequestMapper;
import com.att.university.request.group.GroupAddRequest;
import com.att.university.request.group.GroupUpdateRequest;
import com.att.university.service.GroupService;
import com.att.university.validator.group.GroupAddValidator;
import com.att.university.validator.group.GroupUpdateValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class GroupServiceImpl implements GroupService {
    private static final String GROUP_NOT_FOUND = "Group with Id %d is not found";
    private static final String FACULTY_NOT_FOUND = "Faculty with Id %d is not found";

    private final GroupDao groupDao;
    private final FacultyDao facultyDao;
    private final GroupAddValidator addValidator;
    private final GroupUpdateValidator updateValidator;
    private final GroupAddRequestMapper addRequestMapper;
    private final GroupUpdateRequestMapper updateRequestMapper;

    public List<Group> findAll() {
        return groupDao.findAll(1, groupDao.count());
    }

    @Override
    public Group findById(Integer id) {
        return groupDao.findById(id)
                .orElseThrow(() -> new GroupNotFoundException(String.format(GROUP_NOT_FOUND, id)));
    }

    @Override
    public void create(GroupAddRequest addRequest) {
        log.debug("Group creating with request {}", addRequest);

        addValidator.validate(addRequest);

        Integer facultyId = addRequest.getFacultyId();

        Faculty faculty = facultyDao.findById(facultyId)
                .orElseThrow(() -> new FacultyNotFoundException(String.format(FACULTY_NOT_FOUND, facultyId)));

        groupDao.save(addRequestMapper.convertToEntity(addRequest, faculty));
    }

    @Override
    public void update(GroupUpdateRequest updateRequest) {
        updateValidator.validate(updateRequest);

        if (!groupDao.findById(updateRequest.getId()).isPresent()) {
            throw new GroupNotFoundException(String.format(GROUP_NOT_FOUND, updateRequest.getId()));
        }

        Integer facultyId = updateRequest.getFacultyId();

        Faculty faculty = facultyDao.findById(facultyId)
                .orElseThrow(() -> new FacultyNotFoundException(String.format(FACULTY_NOT_FOUND, facultyId)));

        groupDao.update(updateRequestMapper.convertToEntity(updateRequest, faculty));
    }

    @Override
    public void deleteById(Integer id) {
        if (!groupDao.findById(id).isPresent()) {
            throw new GroupNotFoundException(String.format(GROUP_NOT_FOUND, id));
        }

        log.debug("Group deleting with id {}", id);

        groupDao.deleteById(id);
    }
}
