package com.att.university.request.course;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class CourseAddRequest extends CourseRequest {
    public CourseAddRequest(String name) {
        super(name);
    }
}
