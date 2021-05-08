package com.att.university.request.academic_rank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AcademicRankUpdateRequest {
    private Integer id;
    private String name;
}
