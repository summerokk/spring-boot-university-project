package com.att.request.academic_rank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AcademicRankUpdateRequest {
    @NotNull
    private Integer id;

    @NotBlank(message = "{academic.rank.name}")
    private String name;
}
