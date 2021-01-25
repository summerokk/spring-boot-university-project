package com.att.university.entity;

import java.util.List;
import java.util.Objects;

public class Timetable {
    private final List<Lesson> lessons;

    public Timetable(List<Lesson> lessons) {
        this.lessons = lessons;
    }

    public List<Lesson> getLessons() {
        return lessons;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Timetable timetable = (Timetable) o;
        return Objects.equals(lessons, timetable.lessons);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lessons);
    }

    @Override
    public String toString() {
        return "Timetable{" +
                "lessons=" + lessons +
                '}';
    }
}
