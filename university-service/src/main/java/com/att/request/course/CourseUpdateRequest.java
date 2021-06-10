package com.att.request.course;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseUpdateRequest {
    @NotNull
    private Integer id;

    @NotBlank(message = "{course.name}")
    private String name;
}
