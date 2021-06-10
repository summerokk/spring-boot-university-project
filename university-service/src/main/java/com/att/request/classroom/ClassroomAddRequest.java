package com.att.request.classroom;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClassroomAddRequest {
    @NotNull(message = "{classroom.number}")
    private Integer number;

    @NotNull
    private Integer buildingId;
}
