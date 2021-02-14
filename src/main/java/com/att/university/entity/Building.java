package com.att.university.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Building {
    private final Integer id;
    private final String address;
}
