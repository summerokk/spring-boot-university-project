package com.att.university.dao.impl;

import com.att.university.dao.AcademicRankDao;
import com.att.university.entity.AcademicRank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository("academicRankDao")
public class AcademicRankDaoImpl extends AbstractDaoImpl<AcademicRank> implements AcademicRankDao {
    private static final String SAVE_QUERY = "INSERT INTO academic_ranks(name) VALUES(?)";
    private static final String FIND_BY_ID_QUERY = "SELECT * FROM academic_ranks WHERE id = ?";
    private static final String FIND_ALL_QUERY = "SELECT * FROM academic_ranks OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
    private static final String DELETE_BY_ID_QUERY = "DELETE FROM academic_ranks WHERE id = ?";
    private static final String UPDATE_QUERY = "UPDATE academic_ranks SET name = ? WHERE id = ?";
    private static final String COUNT_QUERY = "SELECT COUNT(*) FROM academic_ranks";

    private static final RowMapper<AcademicRank> ROW_MAPPER = (resultSet, rowNum) ->
            new AcademicRank(resultSet.getInt(1), resultSet.getString("name"));

    @Autowired
    public AcademicRankDaoImpl(DataSource dataSource) {
        super(dataSource, ROW_MAPPER, FIND_BY_ID_QUERY, FIND_ALL_QUERY, DELETE_BY_ID_QUERY, COUNT_QUERY);
    }

    @Override
    protected void insert(AcademicRank academicRank) {
        this.jdbcTemplate.update(SAVE_QUERY, academicRank.getName());
    }

    @Override
    public void update(AcademicRank academicRank) {
        this.jdbcTemplate.update(UPDATE_QUERY, academicRank.getName());
    }
}
