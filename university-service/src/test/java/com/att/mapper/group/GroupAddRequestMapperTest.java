package com.att.mapper.group;

import com.att.entity.Faculty;
import com.att.entity.Group;
import com.att.request.group.GroupAddRequest;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class GroupAddRequestMapperTest {
    private final GroupAddRequestMapper requestMapper = Mappers.getMapper(GroupAddRequestMapper.class);

    @Test
    void convertToEntityShouldReturnEntityWhenRequestContainsAllFields() {
        Faculty faculty = new Faculty(1, "Faculty");

        GroupAddRequest request = new GroupAddRequest("Group", 1);

        Group group = requestMapper.convertToEntity(request, faculty);

        assertEquals("Group", group.getName());
    }

    @Test
    void convertToEntityShouldReturnNullWhenRequestAndFacultyAreNull() {
        Group group = requestMapper.convertToEntity(null, null);

        assertNull(group);
    }

    @Test
    void convertToEntityShouldReturnNullWhenRequestIsNull() {
        Faculty faculty = new Faculty(1, "Faculty");

        Group group = requestMapper.convertToEntity(null, faculty);

        assertNull(group.getName());
        assertEquals(faculty, group.getFaculty());
    }

    @Test
    void convertToEntityShouldReturnNullWhenRequestIsNotNull() {
        GroupAddRequest request = new GroupAddRequest("Group", 1);

        Group group = requestMapper.convertToEntity(request, null);

        assertEquals("Group", group.getName());
        assertNull(group.getFaculty());
    }
}
