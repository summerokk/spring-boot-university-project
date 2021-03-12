package com.att.university.dao.impl;

import com.att.university.dao.StudentDao;
import com.att.university.entity.Student;
import com.att.university.entity.Faculty;
import com.att.university.entity.Group;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Optional;

@Repository("studentDao")
@Slf4j
public class StudentDaoImpl extends AbstractDaoImpl<Student> implements StudentDao {
    private static final String SAVE_QUERY = "INSERT INTO students(first_name, last_name, email, password, group_id) " +
            "VALUES(?, ?, ?, ?, ?)";
    private static final String FIND_ALL_QUERY = "SELECT s.*, g.id as group_id, g.name as group_name, " +
            "f.id as faculty_id, f.name faculty_name " +
            "FROM students s " +
            "JOIN groups g on g.id = s.group_id " +
            "JOIN faculties f on g.faculty_id = f.id OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
    private static final String FIND_BY_ID_QUERY = "SELECT s.*, g.id as group_id, g.name as group_name, " +
            "f.id as faculty_id, f.name faculty_name " +
            "FROM students s " +
            "JOIN groups g on g.id = s.group_id " +
            "JOIN faculties f on g.faculty_id = f.id WHERE s.id = ?";
    private static final String FIND_BY_EMAIL = "SELECT s.*, g.id as group_id, g.name as group_name, " +
            "f.id as faculty_id, f.name faculty_name " +
            "FROM students s " +
            "JOIN groups g on g.id = s.group_id " +
            "JOIN faculties f on g.faculty_id = f.id WHERE s.email = ?";
    private static final String DELETE_BY_ID_QUERY = "DELETE FROM students WHERE id = ?";
    private static final String UPDATE_QUERY = "UPDATE students SET first_name = ?, last_name = ?, email = ?, " +
            "password = ?, group_id = ?  WHERE id = ?";
    private static final String COUNT_QUERY = "SELECT COUNT(*) FROM students";

    private static final RowMapper<Student> ROW_MAPPER = (resultSet, rowNum) -> {
        Faculty faculty = new Faculty(resultSet.getInt("faculty_id"),
                resultSet.getString("faculty_name"));
        Group group = new Group(
                resultSet.getInt("group_id"),
                resultSet.getString("group_name"),
                faculty
        );

        return Student.builder()
                .withId(resultSet.getInt("id"))
                .withGroup(group)
                .withFirstName(resultSet.getString("first_name"))
                .withLastName(resultSet.getString("last_name"))
                .withEmail(resultSet.getString("email"))
                .withPassword(resultSet.getString("password"))
                .build();
    };

    @Autowired
    public StudentDaoImpl(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate, ROW_MAPPER, FIND_BY_ID_QUERY, FIND_ALL_QUERY, DELETE_BY_ID_QUERY, COUNT_QUERY);
    }

    @Override
    protected void insert(Student student) {
        this.jdbcTemplate.update(SAVE_QUERY,
                student.getFirstName(),
                student.getLastName(),
                student.getEmail(),
                student.getPassword(),
                student.getGroup().getId()
        );
    }

    @Override
    public void update(Student student) {
        this.jdbcTemplate.update(UPDATE_QUERY,
                student.getFirstName(),
                student.getLastName(),
                student.getEmail(),
                student.getPassword(),
                student.getGroup().getId(),
                student.getId()
        );
    }

    public Optional<Student> findByEmail(String email) {
        log.debug("Find student by email {}", email);

        return findByParam(email, FIND_BY_EMAIL);
    }
}
