package com.att.request.academic_rank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AcademicRankAddRequest {
    @NotBlank(message = "{academic.rank.name}")
    private String name;
}
