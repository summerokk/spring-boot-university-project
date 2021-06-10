package com.att.service;

import com.att.request.group.GroupAddRequest;
import com.att.request.group.GroupUpdateRequest;
import com.att.entity.Group;

import javax.validation.Valid;
import java.util.List;

public interface GroupService {
    List<Group> findAll();

    Group findById(Integer id);

    void update(@Valid GroupUpdateRequest updateRequest);

    void create(@Valid GroupAddRequest addRequest);

    void deleteById(Integer id);
}
