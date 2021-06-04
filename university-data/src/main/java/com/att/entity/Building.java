package com.att.entity;

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
@Table(name = "buildings")
public class Building {
    @Id
    @SequenceGenerator(
            name = "building_seq",
            sequenceName = "buildings_id_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "building_seq"
    )
    private Integer id;
    private String address;
}
