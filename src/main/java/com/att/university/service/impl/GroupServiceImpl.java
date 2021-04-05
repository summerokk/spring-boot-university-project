package com.att.university.service.impl;

import com.att.university.dao.GroupDao;
import com.att.university.entity.Group;
import com.att.university.service.GroupService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class GroupServiceImpl implements GroupService {
    private final GroupDao groupDao;

    public List<Group> findAll() {
        return groupDao.findAll(1, groupDao.count());
    }
}
