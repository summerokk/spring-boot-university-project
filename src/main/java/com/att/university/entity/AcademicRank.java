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
@Table(name = "academic_ranks")
public class AcademicRank {
    @Id
    @SequenceGenerator(
            name = "academic_rank_seq",
            sequenceName = "academic_ranks_id_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "academic_rank_seq"
    )
    private Integer id;
    private String name;
}
