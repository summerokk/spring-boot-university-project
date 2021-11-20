package com.att.mapper.lesson;

import com.att.entity.Classroom;
import com.att.entity.Course;
import com.att.entity.Group;
import com.att.entity.Lesson;
import com.att.entity.Teacher;
import com.att.request.lesson.LessonAddRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface LessonAddRequestMapper {
    @Mapping(target = "withId", ignore = true)
    @Mapping(target = "withCourse", source = "course")
    @Mapping(target = "withGroup", source = "group")
    @Mapping(target = "withTeacher", source = "teacher")
    @Mapping(target = "withClassroom", source = "classroom")
    @Mapping(target = "withDate", source = "request.date")
    Lesson convertToEntity(LessonAddRequest request, Course course, Group group, Teacher teacher, Classroom classroom);
}
