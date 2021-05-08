package com.att.university.service;

import com.att.university.entity.Group;
import com.att.university.request.group.GroupAddRequest;
import com.att.university.request.group.GroupUpdateRequest;

import java.util.List;

public interface GroupService {
    List<Group> findAll();

    Group findById(Integer id);

    void update(GroupUpdateRequest updateRequest);

    void create(GroupAddRequest addRequest);

    void deleteById(Integer id);
}
