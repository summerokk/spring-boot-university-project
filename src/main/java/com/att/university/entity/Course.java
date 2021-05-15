package com.att.university.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Data
@AllArgsConstructor
@Entity
@NoArgsConstructor
@Table(name = "courses")
public class Course {
    @Id
    @SequenceGenerator(
            name = "course_seq",
            sequenceName = "courses_id_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "course_seq"
    )
    private Integer id;
    private String name;
}
