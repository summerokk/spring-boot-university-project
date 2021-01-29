package com.att.university.dao.impl;

import com.att.university.dao.CourseDao;
import com.att.university.entity.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository("courseDao")
public class CourseDaoImpl extends AbstractDaoImpl<Course> implements CourseDao {
    private static final String SAVE_QUERY = "INSERT INTO courses(name) VALUES(?)";
    private static final String FIND_BY_ID_QUERY = "SELECT * FROM courses WHERE id = ?";
    private static final String FIND_ALL_QUERY = "SELECT * FROM courses";
    private static final String DELETE_BY_ID_QUERY = "DELETE FROM courses WHERE id = ?";
    private static final String UPDATE_QUERY = "UPDATE courses SET name = ? WHERE id = ?";

    private static final RowMapper<Course> ROW_MAPPER = (resultSet, rowNum) ->
            new Course(resultSet.getInt(1), resultSet.getString("name"));

    public CourseDaoImpl() {
        super(ROW_MAPPER, FIND_BY_ID_QUERY, FIND_ALL_QUERY, DELETE_BY_ID_QUERY);
    }

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    protected void insert(Course course) {
        this.jdbcTemplate.update(SAVE_QUERY, course.getName());
    }

    @Override
    public void update(Course course) {
        this.jdbcTemplate.update(UPDATE_QUERY, course.getName());
    }
}
