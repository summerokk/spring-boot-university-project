package com.att.university.dao.impl;

import com.att.university.dao.LessonDao;
import com.att.university.entity.Lesson;
import com.att.university.entity.Building;
import com.att.university.entity.Course;
import com.att.university.entity.Classroom;
import com.att.university.entity.Faculty;
import com.att.university.entity.Group;
import com.att.university.entity.ScienceDegree;
import com.att.university.entity.AcademicRank;
import com.att.university.entity.Teacher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository("lessonDao")
public class LessonDaoImpl extends AbstractDaoImpl<Lesson> implements LessonDao {
    private static final String SAVE_QUERY = "INSERT INTO lessons(course_id, group_id, teacher_id, date, classroom_id)" +
            " VALUES(?, ?, ?, ?, ?)";
    private static final String FIND_ALL_QUERY = "SELECT group_id, l.id as id, classroom_id, teacher_id, date, " +
            "       c.name course_name, course_id, f.id as faculty_id, f.name faculty_name, b.id as building_id, " +
            "       b.address building_address, g.name as group_name, first_name, last_name, c2.number as number, " +
            "       linkedin, email, password, ar.id as academic_rank_id, ar.name as academic_rank_name," +
            "       sc.id as science_degree_id, sc.name as science_degree_name " +
            "FROM lessons l" +
            "    JOIN courses c on l.course_id = c.id" +
            "    JOIN classrooms c2 on c2.id = l.classroom_id" +
            "    JOIN buildings b on b.id = c2.building_id" +
            "    JOIN teachers t on l.teacher_id = t.id" +
            "    JOIN academic_ranks ar on ar.id = t.academic_rank_id" +
            "    JOIN science_degrees sc on t.science_degree_id = sc.id" +
            "    JOIN groups g on l.group_id = g.id" +
            "    JOIN faculties f on g.faculty_id = f.id OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
    private static final String FIND_BY_ID_QUERY = "SELECT group_id, l.id as id, classroom_id, teacher_id, date, " +
            "       c.name course_name, course_id, f.id as faculty_id, f.name faculty_name, b.id as building_id, " +
            "       b.address building_address, g.name as group_name, first_name, last_name, c2.number as number, " +
            "       linkedin, email, password, ar.id as academic_rank_id, ar.name as academic_rank_name," +
            "       sc.id as science_degree_id, sc.name as science_degree_name " +
            "FROM lessons l" +
            "    JOIN courses c on l.course_id = c.id" +
            "    JOIN classrooms c2 on c2.id = l.classroom_id" +
            "    JOIN buildings b on b.id = c2.building_id" +
            "    JOIN teachers t on l.teacher_id = t.id" +
            "    JOIN academic_ranks ar on ar.id = t.academic_rank_id" +
            "    JOIN science_degrees sc on t.science_degree_id = sc.id" +
            "    JOIN groups g on l.group_id = g.id" +
            "    JOIN faculties f on g.faculty_id = f.id WHERE l.id = ?";
    private static final String DELETE_BY_ID_QUERY = "DELETE FROM lessons WHERE id = ?";
    private static final String UPDATE_QUERY = "UPDATE lessons SET course_id = ?, group_id = ?, teacher_id = ?, " +
            "date = ?, classroom_id = ? WHERE id = ?";
    private static final String COUNT_QUERY = "SELECT COUNT(*) FROM lessons";

    private static final RowMapper<Lesson> ROW_MAPPER = (resultSet, rowNum) -> {

        Course course = new Course(resultSet.getInt("course_id"),
                resultSet.getString("course_name"));

        Faculty faculty = new Faculty(resultSet.getInt("faculty_id"),
                resultSet.getString("faculty_name"));
        Group group = new Group(
                resultSet.getInt("group_id"),
                resultSet.getString("group_name"),
                faculty
        );

        Building building = new Building(resultSet.getInt("building_id"),
                resultSet.getString("building_address"));
        Classroom classroom = new Classroom(
                resultSet.getInt("classroom_id"),
                resultSet.getInt("number"),
                building
        );

        AcademicRank academicRank = new AcademicRank(
                resultSet.getInt("academic_rank_id"),
                resultSet.getString("academic_rank_name")
        );

        ScienceDegree scienceDegree = new ScienceDegree(
                resultSet.getInt("science_degree_id"),
                resultSet.getString("science_degree_name")
        );

        Teacher teacher = Teacher.builder()
                .withId(resultSet.getInt("teacher_id"))
                .withLinkedin(resultSet.getString("linkedin"))
                .withAcademicRank(academicRank)
                .withScienceDegree(scienceDegree)
                .withFirstName(resultSet.getString("first_name"))
                .withLastName(resultSet.getString("last_name"))
                .withEmail(resultSet.getString("email"))
                .withPassword(resultSet.getString("password"))
                .build();

        return Lesson.builder()
                .withId(resultSet.getInt("id"))
                .withCourse(course)
                .withGroup(group)
                .withClassroom(classroom)
                .withDate(resultSet.getTimestamp("date").toLocalDateTime())
                .withTeacher(teacher)
                .build();
    };

    @Autowired
    public LessonDaoImpl(DataSource dataSource) {
        super(dataSource, ROW_MAPPER, FIND_BY_ID_QUERY, FIND_ALL_QUERY, DELETE_BY_ID_QUERY, COUNT_QUERY);
    }

    @Override
    protected void insert(Lesson lesson) {
        this.jdbcTemplate.update(SAVE_QUERY,
                lesson.getCourse().getId(),
                lesson.getGroup().getId(),
                lesson.getTeacher().getId(),
                lesson.getDate(),
                lesson.getClassroom().getId()
        );
    }

    @Override
    public void update(Lesson lesson) {
        this.jdbcTemplate.update(UPDATE_QUERY,
                lesson.getCourse().getId(),
                lesson.getGroup().getId(),
                lesson.getTeacher().getId(),
                lesson.getDate(),
                lesson.getClassroom().getId(),
                lesson.getId()
        );
    }
}
