package com.att.mapper.group;

import com.att.entity.Faculty;
import com.att.entity.Group;
import com.att.request.group.GroupUpdateRequest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GroupUpdateRequestMapperTest {
    private final GroupUpdateRequestMapper requestMapper = new GroupUpdateRequestMapper();

    @Test
    void convertToEntityShouldReturnEntityWhenRequestContainsAllFields() {
        Faculty faculty = new Faculty(1, "Faculty");

        GroupUpdateRequest request = new GroupUpdateRequest(1, "Group", 1);

        Group group = requestMapper.convertToEntity(request, faculty);

        assertEquals(1, group.getId());
        assertEquals("Group", group.getName());
        assertEquals(faculty, group.getFaculty());
    }
}
