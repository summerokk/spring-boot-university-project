package com.att.entity;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@NoArgsConstructor
@Getter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
@Table(name = "teachers")
public class Teacher extends Person {
    @Id
    @SequenceGenerator(
            name = "teacher_seq",
            sequenceName = "teachers_id_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "teacher_seq"
    )
    private Integer id;
    private String linkedin;
    @OneToOne
    @JoinColumn(name = "academic_rank_id")
    private AcademicRank academicRank;
    @OneToOne
    @JoinColumn(name = "science_degree_id")
    private ScienceDegree scienceDegree;

    @Builder(setterPrefix = "with")
    public Teacher(Integer id, String firstName, String lastName, String email, String password, String linkedin,
                   ScienceDegree scienceDegree, AcademicRank academicRank) {
        super(firstName, lastName, email, password);

        this.linkedin = linkedin;
        this.scienceDegree = scienceDegree;
        this.academicRank = academicRank;
        this.id = id;
    }
}
