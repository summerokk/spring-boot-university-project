package com.att.university.dao.impl;

import com.att.university.dao.TeacherDao;
import com.att.university.entity.Teacher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.Optional;

@Repository("teacherDao")
@Slf4j
public class TeacherDaoImpl extends AbstractDaoImpl<Teacher> implements TeacherDao {
    private static final String FIND_ALL_QUERY = "select b from Teacher b order by id asc";
    private static final String FIND_BY_EMAIL_QUERY = "select s from Teacher s where s.email = :email";
    private static final String DELETE_BY_ID_QUERY = "delete from Teacher where id = :id";
    private static final String COUNT_QUERY = "select count(id) from Teacher";

    @Autowired
    public TeacherDaoImpl(EntityManager entityManager,
                          @Value("${spring.jpa.properties.hibernate.jdbc.batch_size}") int batchSize) {
        super(entityManager, FIND_ALL_QUERY, DELETE_BY_ID_QUERY, COUNT_QUERY, batchSize);
    }

    @Override
    public Optional<Teacher> findById(Integer id) {
        return Optional.ofNullable(entityManager.find(Teacher.class, id));
    }

    @Override
    @SuppressWarnings("unchecked")
    public Optional<Teacher> findByEmail(String email) {
        log.debug("Find teacher by email {}", email);

        return entityManager.createQuery(FIND_BY_EMAIL_QUERY)
                .setParameter("email", email)
                .getResultList().stream().findFirst();
    }
}
