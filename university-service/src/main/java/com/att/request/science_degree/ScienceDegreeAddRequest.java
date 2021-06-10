package com.att.request.science_degree;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScienceDegreeAddRequest {
    @NotBlank(message = "{science.degree.name}")
    private String name;
}
