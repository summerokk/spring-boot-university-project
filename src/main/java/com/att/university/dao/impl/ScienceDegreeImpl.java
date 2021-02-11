package com.att.university.dao.impl;

import com.att.university.dao.ScienceDegreeDao;
import com.att.university.entity.ScienceDegree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository("scienceDegree")
public class ScienceDegreeImpl extends AbstractDaoImpl<ScienceDegree> implements ScienceDegreeDao {
    private static final String SAVE_QUERY = "INSERT INTO science_degrees(name) VALUES(?)";
    private static final String FIND_ALL_QUERY = "SELECT * FROM science_degrees OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
    private static final String FIND_BY_ID_QUERY = "SELECT * FROM science_degrees WHERE id = ?";
    private static final String DELETE_BY_ID_QUERY = "DELETE FROM science_degrees WHERE id = ?";
    private static final String UPDATE_QUERY = "UPDATE science_degrees SET name = ? WHERE id = ?";
    private static final String COUNT_QUERY = "SELECT COUNT(*) FROM science_degrees";

    private static final RowMapper<ScienceDegree> ROW_MAPPER = (resultSet, rowNum) ->
            new ScienceDegree(
                    resultSet.getInt(1),
                    resultSet.getString("name")
            );

    @Autowired
    public ScienceDegreeImpl(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate, ROW_MAPPER, FIND_BY_ID_QUERY, FIND_ALL_QUERY, DELETE_BY_ID_QUERY, COUNT_QUERY);
    }

    @Override
    protected void insert(ScienceDegree scienceDegree) {
        this.jdbcTemplate.update(SAVE_QUERY, scienceDegree.getName());
    }

    @Override
    public void update(ScienceDegree scienceDegree) {
        this.jdbcTemplate.update(UPDATE_QUERY, scienceDegree.getName(), scienceDegree.getId());
    }
}
