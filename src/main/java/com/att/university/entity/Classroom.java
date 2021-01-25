package com.att.university.entity;

import java.util.Objects;

public class Classroom {
    private final Integer id;
    private final Integer number;
    private final Building building;

    public Classroom(Integer id, Integer number, Building building) {
        this.id = id;
        this.number = number;
        this.building = building;
    }

    public Integer getId() {
        return id;
    }

    public Integer getNumber() {
        return number;
    }

    public Building getBuilding() {
        return building;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Classroom classroom = (Classroom) o;
        return Objects.equals(id, classroom.id) &&
                Objects.equals(number, classroom.number) &&
                Objects.equals(building, classroom.building);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, number, building);
    }

    @Override
    public String toString() {
        return "Classroom{" +
                "id=" + id +
                ", number=" + number +
                ", building=" + building +
                '}';
    }
}
