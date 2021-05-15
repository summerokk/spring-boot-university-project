package com.att.university.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Data
@AllArgsConstructor
@Entity
@NoArgsConstructor
@Table(name = "groups")
public class Group {
    @Id
    @SequenceGenerator(
            name = "group_seq",
            sequenceName = "groups_id_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "group_seq"
    )
    private Integer id;
    private  String name;
    @OneToOne
    private Faculty faculty;
}
