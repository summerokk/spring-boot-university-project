package com.att.university.dao.impl;

import com.att.university.dao.TeacherDao;
import com.att.university.entity.Teacher;
import com.att.university.entity.AcademicRank;
import com.att.university.entity.ScienceDegree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository("teacherDao")
public class TeacherDaoImpl extends AbstractDaoImpl<Teacher> implements TeacherDao {
    private static final String SAVE_QUERY = "INSERT INTO teachers(first_name, last_name, email, password, linkedin, " +
            "academic_rank_id, science_degree_id) VALUES(?, ?, ?, ?, ?, ?, ?)";
    private static final String FIND_ALL_QUERY = "SELECT t.*, " +
            "       ar.id as academic_rank_id, ar.name as academic_rank_name," +
            "       sc.id as science_degree_id, sc.name as science_degree_name " +
            "FROM teachers t" +
            "    JOIN academic_ranks ar on ar.id = t.academic_rank_id" +
            "    JOIN science_degrees sc on t.science_degree_id = sc.id OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
    private static final String FIND_BY_ID_QUERY = "SELECT t.*, " +
            "       ar.id as academic_rank_id, ar.name as academic_rank_name," +
            "       sc.id as science_degree_id, sc.name as science_degree_name " +
            "FROM teachers t" +
            "    JOIN academic_ranks ar on ar.id = t.academic_rank_id" +
            "    JOIN science_degrees sc on t.science_degree_id = sc.id WHERE t.id = ?";
    private static final String DELETE_BY_ID_QUERY = "DELETE FROM teachers WHERE id = ?";
    private static final String UPDATE_QUERY = "UPDATE teachers SET first_name = ?, last_name = ?, email = ?, " +
            "password = ?, linkedin = ?, academic_rank_id = ?, science_degree_id = ?  WHERE id = ?";
    private static final String COUNT_QUERY = "SELECT COUNT(*) FROM teachers";

    private static final RowMapper<Teacher> ROW_MAPPER = (resultSet, rowNum) -> {
        AcademicRank academicRank = new AcademicRank(
                resultSet.getInt("academic_rank_id"),
                resultSet.getString("academic_rank_name")
        );

        ScienceDegree scienceDegree = new ScienceDegree(
                resultSet.getInt("science_degree_id"),
                resultSet.getString("science_degree_name")
        );

        return Teacher.builder()
                .withId(resultSet.getInt("id"))
                .withLinkedin(resultSet.getString("linkedin"))
                .withAcademicRank(academicRank)
                .withScienceDegree(scienceDegree)
                .withFirstName(resultSet.getString("first_name"))
                .withLastName(resultSet.getString("last_name"))
                .withEmail(resultSet.getString("email"))
                .withPassword(resultSet.getString("password"))
                .build();
    };

    @Autowired
    public TeacherDaoImpl(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate, ROW_MAPPER, FIND_BY_ID_QUERY, FIND_ALL_QUERY, DELETE_BY_ID_QUERY, COUNT_QUERY);
    }

    @Override
    protected void insert(Teacher teacher) {
        this.jdbcTemplate.update(SAVE_QUERY,
                teacher.getFirstName(),
                teacher.getLastName(),
                teacher.getEmail(),
                teacher.getPassword(),
                teacher.getLinkedin(),
                teacher.getAcademicRank().getId(),
                teacher.getScienceDegree().getId()
        );
    }

    @Override
    public void update(Teacher teacher) {
        this.jdbcTemplate.update(UPDATE_QUERY,
                teacher.getFirstName(),
                teacher.getLastName(),
                teacher.getEmail(),
                teacher.getPassword(),
                teacher.getLinkedin(),
                teacher.getAcademicRank().getId(),
                teacher.getScienceDegree().getId(),
                teacher.getId()
        );
    }
}
