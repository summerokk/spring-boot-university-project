package com.att.entity;

import lombok.AllArgsConstructor;
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
import java.time.LocalDateTime;

@Builder(setterPrefix = "with", toBuilder = true)
@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "lessons")
public class Lesson {
    @Id
    @SequenceGenerator(
            name = "lesson_seq",
            sequenceName = "lessons_id_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "lesson_seq"
    )
    private Integer id;
    @OneToOne
    private Course course;
    @OneToOne
    private Group group;
    @OneToOne
    private Teacher teacher;
    private LocalDateTime date;
    @OneToOne
    private Classroom classroom;
}
