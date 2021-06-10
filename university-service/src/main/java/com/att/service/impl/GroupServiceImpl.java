package com.att.service.impl;


import com.att.dao.FacultyRepository;
import com.att.dao.GroupRepository;
import com.att.entity.Faculty;
import com.att.entity.Group;
import com.att.exception.dao.FacultyNotFoundException;
import com.att.exception.dao.GroupNotFoundException;
import com.att.mapper.group.GroupAddRequestMapper;
import com.att.mapper.group.GroupUpdateRequestMapper;
import com.att.request.group.GroupAddRequest;
import com.att.request.group.GroupUpdateRequest;
import com.att.service.GroupService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
@Transactional
@Validated
public class GroupServiceImpl implements GroupService {
    private static final String GROUP_NOT_FOUND = "Group with Id %d is not found";
    private static final String FACULTY_NOT_FOUND = "Faculty with Id %d is not found";

    private final GroupRepository groupRepository;
    private final FacultyRepository facultyRepository;
    private final GroupAddRequestMapper addRequestMapper;
    private final GroupUpdateRequestMapper updateRequestMapper;

    public List<Group> findAll() {
        return groupRepository.findAll();
    }

    @Override
    public Group findById(Integer id) {
        return groupRepository.findById(id)
                .orElseThrow(() -> new GroupNotFoundException(String.format(GROUP_NOT_FOUND, id)));
    }

    @Override
    public void create(GroupAddRequest addRequest) {
        log.debug("Group creating with request {}", addRequest);

        Integer facultyId = addRequest.getFacultyId();

        Faculty faculty = facultyRepository.findById(facultyId)
                .orElseThrow(() -> new FacultyNotFoundException(String.format(FACULTY_NOT_FOUND, facultyId)));

        groupRepository.save(addRequestMapper.convertToEntity(addRequest, faculty));
    }

    @Override
    public void update(GroupUpdateRequest updateRequest) {
        if (!groupRepository.findById(updateRequest.getId()).isPresent()) {
            throw new GroupNotFoundException(String.format(GROUP_NOT_FOUND, updateRequest.getId()));
        }

        Integer facultyId = updateRequest.getFacultyId();

        Faculty faculty = facultyRepository.findById(facultyId)
                .orElseThrow(() -> new FacultyNotFoundException(String.format(FACULTY_NOT_FOUND, facultyId)));

        groupRepository.save(updateRequestMapper.convertToEntity(updateRequest, faculty));
    }

    @Override
    public void deleteById(Integer id) {
        if (!groupRepository.findById(id).isPresent()) {
            throw new GroupNotFoundException(String.format(GROUP_NOT_FOUND, id));
        }

        log.debug("Group deleting with id {}", id);

        groupRepository.deleteById(id);
    }
}
