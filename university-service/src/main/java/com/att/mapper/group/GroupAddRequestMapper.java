package com.att.mapper.group;

import com.att.request.group.GroupAddRequest;
import com.att.entity.Faculty;
import com.att.entity.Group;
import org.springframework.stereotype.Component;

@Component
public class GroupAddRequestMapper {
    public Group convertToEntity(GroupAddRequest addRequest, Faculty faculty) {
        return new Group(null, addRequest.getName(), faculty);
    }
}
