package com.att.university.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Classroom {
    private final Integer id;
    private final Integer number;
    private final Building building;
}
