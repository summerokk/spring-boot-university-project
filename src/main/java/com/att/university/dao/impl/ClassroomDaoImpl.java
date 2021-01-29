package com.att.university.dao.impl;

import com.att.university.dao.ClassroomDao;
import com.att.university.entity.Building;
import com.att.university.entity.Classroom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository("classroomDao")
public class ClassroomDaoImpl extends AbstractDaoImpl<Classroom> implements ClassroomDao {
    private static final String SAVE_QUERY = "INSERT INTO classrooms (name, building_id) VALUES(?, ?)";
    private static final String FIND_ALL_QUERY = "SELECT * FROM classrooms c JOIN buildings b on c.building_id = b.id";
    private static final String FIND_BY_ID_QUERY = FIND_ALL_QUERY + " WHERE c.id = ?";
    private static final String DELETE_BY_ID_QUERY = "DELETE FROM classrooms WHERE id = ?";
    private static final String UPDATE_QUERY = "UPDATE classrooms SET number = ? and building_id = ? WHERE id = ?";

    private static final RowMapper<Classroom> ROW_MAPPER = (resultSet, rowNum) -> {
        Building building = new Building(resultSet.getInt(1), resultSet.getString("address"));

        return new Classroom(
                resultSet.getInt(1),
                resultSet.getInt("number"),
                building
        );
    };

    public ClassroomDaoImpl() {
        super(ROW_MAPPER, FIND_BY_ID_QUERY, FIND_ALL_QUERY, DELETE_BY_ID_QUERY);
    }

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    protected void insert(Classroom classroom) {
        this.jdbcTemplate.update(SAVE_QUERY, classroom.getNumber(), classroom.getBuilding().getId());
    }

    @Override
    public void update(Classroom classroom) {
        this.jdbcTemplate.update(UPDATE_QUERY, classroom.getNumber(), classroom.getBuilding().getId(), classroom.getId());
    }
}
