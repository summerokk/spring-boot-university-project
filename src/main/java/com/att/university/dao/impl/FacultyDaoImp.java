package com.att.university.dao.impl;

import com.att.university.dao.FacultyDao;
import com.att.university.entity.Faculty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository("facultyDao")
public class FacultyDaoImp extends AbstractDaoImpl<Faculty> implements FacultyDao {
    private static final String SAVE_QUERY = "INSERT INTO faculties(name) VALUES(?)";
    private static final String FIND_ALL_QUERY = "SELECT * FROM faculties OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
    private static final String FIND_BY_ID_QUERY = "SELECT * FROM faculties WHERE id = ?";
    private static final String DELETE_BY_ID_QUERY = "DELETE FROM faculties WHERE id = ?";
    private static final String UPDATE_QUERY = "UPDATE faculties SET name = ? WHERE id = ?";
    private static final String COUNT_QUERY = "SELECT COUNT(*) FROM faculties";

    private static final RowMapper<Faculty> ROW_MAPPER = (resultSet, rowNum) ->
            new Faculty(resultSet.getInt(1), resultSet.getString("name"));

    @Autowired
    public FacultyDaoImp(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate, ROW_MAPPER, FIND_BY_ID_QUERY, FIND_ALL_QUERY, DELETE_BY_ID_QUERY, COUNT_QUERY);
    }

    @Override
    protected void insert(Faculty faculty) {
        this.jdbcTemplate.update(SAVE_QUERY, faculty.getName());
    }

    @Override
    public void update(Faculty faculty) {
        this.jdbcTemplate.update(UPDATE_QUERY, faculty.getName(), faculty.getId());
    }
}
