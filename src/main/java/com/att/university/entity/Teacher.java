package com.att.university.entity;

import java.util.Objects;

public class Teacher extends Person {
    private final String linkedin;
    private final AcademicRank academicRank;
    private final ScienceDegree scienceDegree;

    public Teacher(Builder builder) {
        super(builder);

        this.linkedin = builder.linkedin;
        this.academicRank = builder.academicRank;
        this.scienceDegree = builder.scienceDegree;
    }

    public static Builder builder() {
        return new Builder();
    }

    public String getLinkedin() {
        return linkedin;
    }

    public AcademicRank getAcademicRank() {
        return academicRank;
    }

    public ScienceDegree getScienceDegree() {
        return scienceDegree;
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

        Teacher teacher = (Teacher) o;
        return Objects.equals(linkedin, teacher.linkedin) &&
                Objects.equals(academicRank, teacher.academicRank) &&
                Objects.equals(scienceDegree, teacher.scienceDegree);
    }

    @Override
    public int hashCode() {
        return Objects.hash(linkedin, academicRank, scienceDegree) + super.hashCode();
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "id=" + this.getId() +
                ", firstName=" + this.getFirstName() +
                ", lastName=" + this.getLastName() +
                ", email=" + this.getEmail() +
                ", password=" + this.getPassword() +
                ", linkedin='" + linkedin + '\'' +
                ", academicRank=" + academicRank +
                ", scienceDegree=" + scienceDegree +
                '}';
    }

    public static class Builder extends Person.Builder<Builder> {
        private String linkedin;
        private AcademicRank academicRank;
        private ScienceDegree scienceDegree;

        public Builder() {

        }

        public Teacher build() {
            return new Teacher(this);
        }

        public Builder withLinkedin(String linkedin) {
            this.linkedin = linkedin;
            return this;
        }

        public Builder withAcademicRank(AcademicRank academicRank) {
            this.academicRank = academicRank;
            return this;
        }

        public Builder withScienceDegree(ScienceDegree scienceDegree) {
            this.scienceDegree = scienceDegree;
            return this;
        }
    }
}
