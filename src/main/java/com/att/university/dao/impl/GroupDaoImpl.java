package com.att.university.dao.impl;

import com.att.university.dao.GroupDao;
import com.att.university.entity.Faculty;
import com.att.university.entity.Group;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository("groupDao")
public class GroupDaoImpl extends AbstractDaoImpl<Group> implements GroupDao {
    private static final String SAVE_QUERY = "INSERT INTO groups(name, faculty_id) VALUES(?, ?)";
    private static final String FIND_ALL_QUERY = "SELECT g.*, f.id as faculty_id, f.name as faculty_name " +
            "FROM groups g " +
            "JOIN faculties f on g.faculty_id = f.id OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
    private static final String FIND_BY_ID_QUERY = "SELECT g.*, f.id as faculty_id, f.name as faculty_name " +
            "FROM groups g " +
            "JOIN faculties f on g.faculty_id = f.id WHERE g.id = ?";
    private static final String DELETE_BY_ID_QUERY = "DELETE FROM groups WHERE id = ?";
    private static final String UPDATE_QUERY = "UPDATE groups SET name = ?, faculty_id = ? WHERE id = ?";
    private static final String COUNT_QUERY = "SELECT COUNT(*) FROM groups";

    private static final RowMapper<Group> ROW_MAPPER = (resultSet, rowNum) -> {
        Faculty faculty = new Faculty(resultSet.getInt("faculty_id"),
                resultSet.getString("faculty_name"));

        return new Group(
                resultSet.getInt(1),
                resultSet.getString("name"),
                faculty
        );
    };

    @Autowired
    public GroupDaoImpl(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate, ROW_MAPPER, FIND_BY_ID_QUERY, FIND_ALL_QUERY, DELETE_BY_ID_QUERY, COUNT_QUERY);
    }

    @Override
    protected void insert(Group group) {
        this.jdbcTemplate.update(SAVE_QUERY, group.getName(), group.getFaculty().getId());
    }

    @Override
    public void update(Group group) {
        this.jdbcTemplate.update(UPDATE_QUERY, group.getName(), group.getFaculty().getId(), group.getId());
    }
}
