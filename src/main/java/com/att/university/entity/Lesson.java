package com.att.university.entity;

import java.time.LocalDateTime;
import java.util.Objects;

public class Lesson {
    private final Integer id;
    private final Course course;
    private final Group group;
    private final Teacher teacher;
    private final LocalDateTime date;
    private final Classroom classroom;

    public Lesson(Builder builder) {
        this.id = builder.id;
        this.course = builder.course;
        this.group = builder.group;
        this.teacher = builder.teacher;
        this.date = builder.date;
        this.classroom = builder.classroom;
    }

    public Integer getId() {
        return id;
    }

    public Course getCourse() {
        return course;
    }

    public Group getGroup() {
        return group;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public Classroom getClassroom() {
        return classroom;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static Builder builder(Lesson lesson) {
        return new Builder(lesson);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Lesson lesson = (Lesson) o;
        return Objects.equals(id, lesson.id) &&
                Objects.equals(course, lesson.course) &&
                Objects.equals(group, lesson.group) &&
                Objects.equals(teacher, lesson.teacher) &&
                Objects.equals(date, lesson.date) &&
                Objects.equals(classroom, lesson.classroom);
    }

    @Override
    public String toString() {
        return "Lesson{" +
                "id=" + id +
                ", course=" + course +
                ", group=" + group +
                ", teacher=" + teacher +
                ", date=" + date +
                ", classroom=" + classroom +
                "}\n";
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, course, group, teacher, date, classroom);
    }

    public static class Builder {
        private Integer id;
        private Course course;
        private Group group;
        private Teacher teacher;
        private LocalDateTime date;
        private Classroom classroom;

        private Builder() {

        }

        private Builder(Lesson lesson) {
            this.id = lesson.id;
            this.course = lesson.course;
            this.group = lesson.group;
            this.teacher = lesson.teacher;
            this.date = lesson.date;
            this.classroom = lesson.classroom;
        }

        public Lesson build() {
            return new Lesson(this);
        }


        public Builder withId(Integer id) {
            this.id = id;
            return this;
        }

        public Builder withCourse(Course course) {
            this.course = course;
            return this;
        }

        public Builder withGroup(Group group) {
            this.group = group;
            return this;
        }

        public Builder withTeacher(Teacher teacher) {
            this.teacher = teacher;
            return this;
        }

        public Builder withDate(LocalDateTime date) {
            this.date = date;
            return this;
        }

        public Builder withClassroom(Classroom classroom) {
            this.classroom = classroom;
            return this;
        }
    }
}
