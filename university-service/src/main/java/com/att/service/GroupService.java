package com.att.service;

import com.att.request.group.GroupAddRequest;
import com.att.request.group.GroupUpdateRequest;
import com.att.entity.Group;

import java.util.List;

public interface GroupService {
    List<Group> findAll();

    Group findById(Integer id);

    void update(GroupUpdateRequest updateRequest);

    void create(GroupAddRequest addRequest);

    void deleteById(Integer id);
}
