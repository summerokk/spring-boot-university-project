package com.att.university.mapper.group;

import com.att.university.entity.Faculty;
import com.att.university.entity.Group;
import com.att.university.request.group.GroupAddRequest;
import org.springframework.stereotype.Component;

@Component
public class GroupAddRequestMapper {
    public Group convertToEntity(GroupAddRequest addRequest, Faculty faculty) {
        return new Group(null, addRequest.getName(), faculty);
    }
}
