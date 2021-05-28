create sequence academic_ranks_id_seq;

alter sequence academic_ranks_id_seq owner to postgres;

create sequence buildings_id_seq;

alter sequence buildings_id_seq owner to postgres;

create sequence classrooms_id_seq;

alter sequence classrooms_id_seq owner to postgres;

create sequence courses_id_seq;

alter sequence courses_id_seq owner to postgres;

create sequence faculties_id_seq;

alter sequence faculties_id_seq owner to postgres;

create sequence groups_id_seq;

alter sequence groups_id_seq owner to postgres;

create sequence lessons_id_seq;

alter sequence lessons_id_seq owner to postgres;

create sequence science_degrees_id_seq;

alter sequence science_degrees_id_seq owner to postgres;

create sequence students_id_seq;

alter sequence students_id_seq owner to postgres;

create sequence teachers_id_seq;

alter sequence teachers_id_seq owner to postgres;

create table if not exists academic_ranks
(
    id integer not null
        constraint academic_ranks_pkey
            primary key,
    name varchar(255)
);

alter table academic_ranks owner to postgres;

create table if not exists buildings
(
    id integer not null
        constraint buildings_pkey
            primary key,
    address varchar(255)
);

alter table buildings owner to postgres;

create table if not exists classrooms
(
    id integer not null
        constraint classrooms_pkey
            primary key,
    number integer,
    building_id integer
        constraint fkp169e84csib3wbj8ipuh3omkg
            references buildings
);

alter table classrooms owner to postgres;

create table if not exists courses
(
    id integer not null
        constraint courses_pkey
            primary key,
    name varchar(255)
);

alter table courses owner to postgres;

create table if not exists faculties
(
    id integer not null
        constraint faculties_pkey
            primary key,
    name varchar(255)
);

alter table faculties owner to postgres;

create table if not exists groups
(
    id integer not null
        constraint groups_pkey
            primary key,
    name varchar(255),
    faculty_id integer
        constraint fkh01idhhv5c633dhk8kyptns82
            references faculties
);

alter table groups owner to postgres;

create table if not exists science_degrees
(
    id integer not null
        constraint science_degrees_pkey
            primary key,
    name varchar(255)
);

alter table science_degrees owner to postgres;

create table if not exists students
(
    id integer not null
        constraint students_pkey
            primary key,
    email varchar(255),
    first_name varchar(255),
    last_name varchar(255),
    password varchar(255),
    group_id integer
        constraint fkmsev1nou0j86spuk5jrv19mss
            references groups
);

alter table students owner to postgres;

create table if not exists teachers
(
    id integer not null
        constraint teachers_pkey
            primary key,
    email varchar(255),
    first_name varchar(255),
    last_name varchar(255),
    password varchar(255),
    linkedin varchar(255),
    academic_rank_id integer
        constraint fkdmrjxp1372ph0nm8kfv5qkjyl
            references academic_ranks,
    science_degree_id integer
        constraint fk6v4p7owkj0ga6vilbk38il3kn
            references science_degrees
);

alter table teachers owner to postgres;

create table if not exists lessons
(
    id integer not null
        constraint lessons_pkey
            primary key,
    date timestamp,
    classroom_id integer
        constraint fkbffxqtymudjwdb39m7dnjn4ey
            references classrooms,
    course_id integer
        constraint fk17ucc7gjfjddsyi0gvstkqeat
            references courses,
    group_id integer
        constraint fktdolsaotaqlwxbxwaxt00kimk
            references groups,
    teacher_id integer
        constraint fkbr76cuebuufbbltujpfq04tto
            references teachers
);

alter table lessons owner to postgres;
