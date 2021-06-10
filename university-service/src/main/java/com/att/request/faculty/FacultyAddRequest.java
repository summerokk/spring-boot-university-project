package com.att.request.faculty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FacultyAddRequest {
    @NotBlank(message = "{faculty.name}")
    private String name;
}
