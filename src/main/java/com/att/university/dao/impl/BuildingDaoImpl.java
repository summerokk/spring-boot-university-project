package com.att.university.dao.impl;

import com.att.university.dao.BuildingDao;
import com.att.university.entity.Building;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository("buildingDao")
public class BuildingDaoImpl extends AbstractDaoImpl<Building> implements BuildingDao {
    private static final String SAVE_QUERY = "INSERT INTO buildings(address) VALUES(?)";
    private static final String FIND_ALL_QUERY = "SELECT * FROM buildings";
    private static final String FIND_BY_ID_QUERY = FIND_ALL_QUERY + " WHERE id = ?";
    private static final String DELETE_BY_ID_QUERY = "DELETE FROM buildings WHERE id = ?";
    private static final String UPDATE_QUERY = "UPDATE buildings SET address = ? WHERE id = ?";

    private static final RowMapper<Building> ROW_MAPPER = (resultSet, rowNum) ->
            new Building(resultSet.getInt(1), resultSet.getString("address"));

    public BuildingDaoImpl() {
        super(ROW_MAPPER, FIND_BY_ID_QUERY, FIND_ALL_QUERY, DELETE_BY_ID_QUERY);
    }

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    protected void insert(Building building) {
        this.jdbcTemplate.update(SAVE_QUERY, building.getAddress());
    }

    @Override
    public void update(Building building) {
        this.jdbcTemplate.update(UPDATE_QUERY, building.getAddress());
    }
}
