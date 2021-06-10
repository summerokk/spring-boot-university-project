package com.att.request.science_degree;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScienceDegreeUpdateRequest {
    @NotNull
    private Integer id;

    @NotBlank(message = "{science.degree.name}")
    private String name;
}
