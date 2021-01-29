package com.att.university.dao.impl;

import com.att.university.dao.FacultyDao;
import com.att.university.entity.Faculty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository("facultyDao")
public class FacultyDaoImp extends AbstractDaoImpl<Faculty> implements FacultyDao {
    private static final String SAVE_QUERY = "INSERT INTO faculties(name) VALUES(?)";
    private static final String FIND_ALL_QUERY = "SELECT * FROM faculties";
    private static final String FIND_BY_ID_QUERY = FIND_ALL_QUERY + " WHERE id = ?";
    private static final String DELETE_BY_ID_QUERY = "DELETE FROM faculties WHERE id = ?";
    private static final String UPDATE_QUERY = "UPDATE faculties SET name = ? WHERE id = ?";

    private static final RowMapper<Faculty> ROW_MAPPER = (resultSet, rowNum) ->
            new Faculty(resultSet.getInt(1), resultSet.getString("name"));

    public FacultyDaoImp() {
        super(ROW_MAPPER, FIND_BY_ID_QUERY, FIND_ALL_QUERY, DELETE_BY_ID_QUERY);
    }

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    protected void insert(Faculty faculty) {
        this.jdbcTemplate.update(SAVE_QUERY, faculty.getName());
    }

    @Override
    public void update(Faculty faculty) {
        this.jdbcTemplate.update(UPDATE_QUERY, faculty.getName());
    }
}
