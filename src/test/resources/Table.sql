DROP TABLE IF EXISTS lessons;
DROP TABLE IF EXISTS teachers;
DROP TABLE IF EXISTS students;
DROP TABLE IF EXISTS classrooms;
DROP TABLE IF EXISTS groups;
DROP TABLE IF EXISTS faculties;
DROP TABLE IF EXISTS academic_ranks;
DROP TABLE IF EXISTS courses;
DROP TABLE IF EXISTS buildings;
DROP TABLE IF EXISTS science_degrees;

create table if not exists academic_ranks
(
    id   serial not null
        constraint academic_ranks_pkey
            primary key,
    name varchar(255)
);


create table if not exists buildings
(
    id      serial not null
        constraint buildings_pkey
            primary key,
    address varchar(255)
);


create table if not exists classrooms
(
    id          serial not null
        constraint classrooms_pkey
            primary key,
    number      integer,
    building_id integer
        constraint fkp169e84csib3wbj8ipuh3omkg
            references buildings
            on delete cascade
);


create table if not exists courses
(
    id   serial not null
        constraint courses_pkey
            primary key,
    name varchar(255)
);


create table if not exists faculties
(
    id   serial not null
        constraint faculties_pkey
            primary key,
    name varchar(255)
);


create table if not exists groups
(
    id         serial not null
        constraint groups_pkey
            primary key,
    name       varchar(255),
    faculty_id integer
        constraint fkh01idhhv5c633dhk8kyptns82
            references faculties
            on delete cascade
);


create table if not exists science_degrees
(
    id   serial not null
        constraint science_degrees_pkey
            primary key,
    name varchar(255)
);


create table if not exists students
(
    id         serial not null
        constraint students_pkey
            primary key,
    email      varchar(255),
    first_name varchar(255),
    last_name  varchar(255),
    password   varchar(255),
    group_id   integer
        constraint fkmsev1nou0j86spuk5jrv19mss
            references groups
            on delete cascade
);


create table if not exists teachers
(
    id                serial not null
        constraint teachers_pkey
            primary key,
    email             varchar(255),
    first_name        varchar(255),
    last_name         varchar(255),
    password          varchar(255),
    linkedin          varchar(255),
    academic_rank_id  integer
        constraint fkdmrjxp1372ph0nm8kfv5qkjyl
            references academic_ranks
            on delete cascade,
    science_degree_id integer
        constraint fk6v4p7owkj0ga6vilbk38il3kn
            references science_degrees
            on delete cascade
);


create table if not exists lessons
(
    id           serial not null
        constraint lessons_pkey
            primary key,
    date         timestamp,
    classroom_id integer
        constraint fkbffxqtymudjwdb39m7dnjn4ey
            references classrooms
            on delete cascade,
    course_id    integer
        constraint fk17ucc7gjfjddsyi0gvstkqeat
            references courses
            on delete cascade,
    group_id     integer
        constraint fktdolsaotaqlwxbxwaxt00kimk
            references groups
            on delete cascade,
    teacher_id   integer
        constraint fkbr76cuebuufbbltujpfq04tto
            references teachers
            on delete cascade
);


