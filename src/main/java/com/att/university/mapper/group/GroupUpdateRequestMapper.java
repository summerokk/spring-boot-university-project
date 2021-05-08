package com.att.university.mapper.group;

import com.att.university.entity.Faculty;
import com.att.university.entity.Group;
import com.att.university.request.group.GroupUpdateRequest;
import org.springframework.stereotype.Component;

@Component
public class GroupUpdateRequestMapper {
    public Group convertToEntity(GroupUpdateRequest updateRequest, Faculty faculty) {
        return new Group(updateRequest.getId(), updateRequest.getName(), faculty);
    }
}
