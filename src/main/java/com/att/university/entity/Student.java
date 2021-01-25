package com.att.university.entity;

import java.util.Objects;

public class Student extends Person {
    private final Group group;

    public Student(Builder builder) {
        super(builder);
        this.group = builder.group;
    }

    public Group getGroup() {
        return group;
    }

    public static Builder builder() {
        return new Builder();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        if (!super.equals(o)) {
            return false;
        }

        Student student = (Student) o;
        return Objects.equals(group, student.group);
    }

    @Override
    public int hashCode() {
        return Objects.hash(group) + super.hashCode();
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + this.getId() +
                ", firstName = " + this.getFirstName() +
                ", lastName = " + this.getLastName() +
                ", email = " + this.getEmail() +
                ", password = " + this.getPassword() +
                ", group = " + group +
                '}';
    }

    public static class Builder extends Person.Builder<Builder> {
        private Group group;

        public Builder() {

        }

        public Student build() {
            return new Student(this);
        }

        public Builder withGroup(Group group) {
            this.group = group;
            return this;
        }
    }
}
