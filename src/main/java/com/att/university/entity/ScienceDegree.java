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
@Table(name = "science_degrees")
public class ScienceDegree {
    @Id
    @SequenceGenerator(
            name = "science_degree_seq",
            sequenceName = "science_degrees_id_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "science_degree_seq"
    )
    private Integer id;
    private String name;
}
