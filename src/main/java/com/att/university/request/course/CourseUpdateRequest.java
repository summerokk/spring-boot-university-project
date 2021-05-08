package com.att.university.request.course;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class CourseUpdateRequest extends CourseRequest {
    private Integer id;

    public CourseUpdateRequest(Integer id, String name) {
        super(name);

        this.id = id;
    }
}
