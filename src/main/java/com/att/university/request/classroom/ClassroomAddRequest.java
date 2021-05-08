package com.att.university.request.classroom;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClassroomAddRequest {
    private Integer number;
    private Integer buildingId;
}
