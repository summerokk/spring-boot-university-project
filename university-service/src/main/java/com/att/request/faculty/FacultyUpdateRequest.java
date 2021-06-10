package com.att.request.faculty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FacultyUpdateRequest {
    @NotNull
    private Integer id;

    @NotBlank(message = "{faculty.name}")
    private String name;
}
