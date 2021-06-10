package com.att.request.group;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupUpdateRequest {
    @NotNull
    private Integer id;

    @NotBlank(message = "{group.name}")
    private String name;

    @NotNull
    private Integer facultyId;
}
