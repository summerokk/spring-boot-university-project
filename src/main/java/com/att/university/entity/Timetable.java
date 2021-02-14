package com.att.university.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class Timetable {
    private final List<Lesson> lessons;
}
