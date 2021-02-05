INSERT INTO buildings(address) VALUES ('Kirova 32');
INSERT INTO buildings(address) VALUES ('Pertova 42');

INSERT INTO classrooms(number, building_id) VALUES (12, 1);
INSERT INTO classrooms(number, building_id) VALUES (13, 1);
INSERT INTO classrooms(number, building_id) VALUES (131, 2);

INSERT INTO academic_ranks(name) VALUES ('Assistant Professor');
INSERT INTO academic_ranks(name) VALUES ('Full Professor');
INSERT INTO academic_ranks(name) VALUES ('Endowed Professor');

INSERT INTO science_degrees(name) VALUES ('Associate degree');
INSERT INTO science_degrees(name) VALUES ('Doctoral degree');
INSERT INTO science_degrees(name) VALUES ('Bachelor''s degree');
INSERT INTO science_degrees(name) VALUES ('Master''s degree');

INSERT INTO faculties(name) VALUES ('School of Visual arts');
INSERT INTO faculties(name) VALUES ('Department of Geography');
INSERT INTO faculties(name) VALUES ('Department of Plant Science');

INSERT INTO courses(name) VALUES ('Special Topics in Agronomy');
INSERT INTO courses(name) VALUES ('Course 2');
INSERT INTO courses(name) VALUES ('Course 3');
INSERT INTO courses(name) VALUES ('Course 4');
INSERT INTO courses(name) VALUES ('Course 5');
INSERT INTO courses(name) VALUES ('Course 6');

INSERT INTO groups(name, faculty_id) VALUES ('GT-23', 1);
INSERT INTO groups(name, faculty_id) VALUES ('HT-22', 2);
INSERT INTO groups(name, faculty_id) VALUES ('HY-53', 3);

INSERT INTO students(first_name, last_name, email, password, group_id) VALUES ('Fedor', 'Tolov', 'tolof234@tmail.com', 'password', 1);
INSERT INTO students(first_name, last_name, email, password, group_id) VALUES ('Anton', 'Petrov', 'p.anton@tmail.com', 'password', 1);
INSERT INTO students(first_name, last_name, email, password, group_id) VALUES ('Oleg', 'Zotov', 'zotofff322@tmail.com', 'password', 2);
INSERT INTO students(first_name, last_name, email, password, group_id) VALUES ('Vadim', 'Loginov', 'unusob@tmail.com', 'password', 3);

INSERT INTO teachers(first_name, last_name, email, password, academic_rank_id, science_degree_id, linkedin) VALUES ('Fedor', 'Tolov', 'tolof234@tmail.com', 'password', 1, 2, 'https://link.ru');
INSERT INTO teachers(first_name, last_name, email, password, academic_rank_id, science_degree_id, linkedin) VALUES ('Alex', 'Popov', 'email234@tmail.com', 'password', 2, 1, 'https://link.ru');

INSERT INTO lessons(course_id, group_id, teacher_id, date, classroom_id) VALUES (2, 1, 1, '2004-10-19 10:23', 1);
INSERT INTO lessons(course_id, group_id, teacher_id, date, classroom_id) VALUES (1, 2, 2, '2004-10-19 10:23', 2);