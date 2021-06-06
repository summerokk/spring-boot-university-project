package com.att.mapper.group;

import com.att.request.group.GroupUpdateRequest;
import com.att.entity.Faculty;
import com.att.entity.Group;
import org.springframework.stereotype.Component;

@Component
public class GroupUpdateRequestMapper {
    public Group convertToEntity(GroupUpdateRequest updateRequest, Faculty faculty) {
        return new Group(updateRequest.getId(), updateRequest.getName(), faculty);
    }
}
