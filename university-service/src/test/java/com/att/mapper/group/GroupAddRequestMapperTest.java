package com.att.mapper.group;

import com.att.entity.Faculty;
import com.att.entity.Group;
import com.att.request.group.GroupAddRequest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GroupAddRequestMapperTest {
    private final GroupAddRequestMapper requestMapper = new GroupAddRequestMapper();

    @Test
    void convertToEntityShouldReturnEntityWhenRequestContainsAllFields() {
        Faculty faculty = new Faculty(1, "Faculty");

        GroupAddRequest request = new GroupAddRequest("Group", 1);

        Group group = requestMapper.convertToEntity(request, faculty);

        assertEquals("Group", group.getName());
        assertEquals(faculty, group.getFaculty());
    }
}
