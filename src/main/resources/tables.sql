DROP TABLE IF EXISTS academic_ranks;
DROP TABLE IF EXISTS groups;
DROP TABLE IF EXISTS students;
DROP TABLE IF EXISTS teachers;
DROP TABLE IF EXISTS courses;
DROP TABLE IF EXISTS faculties;
DROP TABLE IF EXISTS buildings;
DROP TABLE IF EXISTS classrooms;
DROP TABLE IF EXISTS science_degrees;
DROP TABLE IF EXISTS lessons;

CREATE TABLE academic_ranks
(
    id   SERIAL,
    name varchar(100)
);

CREATE TABLE faculties
(
    id   SERIAL,
    name varchar(100)
);

CREATE TABLE groups
(
    id         SERIAL,
    name       varchar(100),
    faculty_id int
);

CREATE TABLE buildings
(
    id      SERIAL,
    address varchar(100)
);

CREATE TABLE classrooms
(
    id          SERIAL,
    number      varchar(100),
    building_id int
);

CREATE TABLE courses
(
    id   SERIAL,
    name varchar(100)
);

CREATE TABLE science_degrees
(
    id   SERIAL,
    name varchar(100)
);

CREATE TABLE students
(
    id         SERIAL,
    first_name varchar(100),
    last_name  varchar(100),
    email      varchar(100),
    password   varchar(100),
    group_id   int
);

CREATE TABLE teachers
(
    id                SERIAL,
    first_name        varchar(100),
    last_name         varchar(100),
    email             varchar(100),
    password          varchar(100),
    linkedin          varchar(255),
    academic_rank_id  int,
    science_degree_id int
);

CREATE TABLE lessons
(
    id           SERIAL,
    course_id    int,
    group_id     int,
    teacher_id   int,
    date         timestamp,
    classroom_id int
);
