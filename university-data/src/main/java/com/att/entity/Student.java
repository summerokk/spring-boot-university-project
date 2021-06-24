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
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@NoArgsConstructor
@Getter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
@Table(name = "students")
public class Student extends Person {
    @Id
    @SequenceGenerator(
            name = "student_seq",
            sequenceName = "students_id_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "student_seq"
    )
    private Integer id;
    @OneToOne
    private Group group;

    @Builder(setterPrefix = "with")
    public Student(Integer id, String firstName, String lastName, String email, String password, Group group) {
        super(firstName, lastName, email, password);

        this.group = group;
        this.id = id;
    }
}
