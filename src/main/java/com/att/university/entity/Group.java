package com.att.university.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Group {
    private final Integer id;
    private final String name;
    private final Faculty faculty;
}
