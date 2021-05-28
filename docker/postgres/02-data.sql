INSERT INTO buildings(id, address)
VALUES (1, 'Kirova 32');
INSERT INTO buildings(id, address)
VALUES (2, 'Pertova 42');
INSERT INTO buildings(id, address)
VALUES (3, 'Pertova 2');

INSERT INTO classrooms(id, number, building_id)
VALUES (1, 12, 1);
INSERT INTO classrooms(id, number, building_id)
VALUES (2, 13, 1);
INSERT INTO classrooms(id, number, building_id)
VALUES (3, 131, 2);

INSERT INTO academic_ranks(id, name)
VALUES (1, 'Assistant Professor');
INSERT INTO academic_ranks(id, name)
VALUES (2, 'Full Professor');
INSERT INTO academic_ranks(id, name)
VALUES (3, 'Endowed Professor');

INSERT INTO science_degrees(id, name)
VALUES (1, 'Associate degree');
INSERT INTO science_degrees(id, name)
VALUES (2, 'Doctoral degree');
INSERT INTO science_degrees(id, name)
VALUES (3, 'Bachelor''s degree');
INSERT INTO science_degrees(id, name)
VALUES (4, 'Master''s degree');

INSERT INTO faculties(id, name)
VALUES (1, 'School of Visual arts');
INSERT INTO faculties(id, name)
VALUES (2, 'Department of Geography');
INSERT INTO faculties(id, name)
VALUES (3, 'Department of Plant Science');
INSERT INTO faculties(id, name)
VALUES (4, 'Geography');

INSERT INTO courses(id, name)
VALUES (1, 'Special Topics in Agronomy');
INSERT INTO courses(id, name)
VALUES (2, 'Math');
INSERT INTO courses(id, name)
VALUES (3, 'Biology');


INSERT INTO groups(id, name, faculty_id)
VALUES (1, 'GT-23', 1);
INSERT INTO groups(id, name, faculty_id)
VALUES (2, 'HT-22', 2);
INSERT INTO groups(id, name, faculty_id)
VALUES (3, 'HY-53', 3);

INSERT INTO students(id, first_name, last_name, email, password, group_id)
VALUES (1, 'Fedor', 'Tolov', 'tolof234@tmail.com', 'password', 1);
INSERT INTO students(id, first_name, last_name, email, password, group_id)
VALUES (2, 'Anton', 'Petrov', 'p.anton@tmail.com', 'password', 1);
INSERT INTO students(id, first_name, last_name, email, password, group_id)
VALUES (3, 'Fedor', 'Petrov', 'anton@tmail.com', 'password', null);

INSERT INTO teachers(id, first_name, last_name, email, password, academic_rank_id, science_degree_id, linkedin)
VALUES (1, 'Fedor', 'Tolov', 'tolof234@tmail.com', 'password', 1, 2, 'https://link.ru');
INSERT INTO teachers(id, first_name, last_name, email, password, academic_rank_id, science_degree_id, linkedin)
VALUES (2, 'Alex', 'Popov', 'email234@tmail.com', 'password', 1, 2, 'https://link.ru');
INSERT INTO teachers(id, first_name, last_name, email, password, academic_rank_id, science_degree_id, linkedin)
VALUES (3, 'Dima', 'Antipov', 'ant213@tmail.com', 'password', 1, 2, 'https://link.ru');

INSERT INTO lessons(id, course_id, group_id, teacher_id, date, classroom_id)
VALUES (1, 1, 1, 1, '2004-10-19 10:23', 1);
INSERT INTO lessons(id, course_id, group_id, teacher_id, date, classroom_id)
VALUES (2, 2, 2, 1, '2004-10-20 10:23', 1);
INSERT INTO lessons(id, course_id, group_id, teacher_id, date, classroom_id)
VALUES (3, 2, 2, 1, '2020-10-20 10:23', 1);
