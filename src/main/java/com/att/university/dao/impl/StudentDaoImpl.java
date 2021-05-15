package com.att.university.dao.impl;

import com.att.university.dao.StudentDao;
import com.att.university.entity.Student;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.Optional;

@Repository("studentDao")
@Slf4j
public class StudentDaoImpl extends AbstractDaoImpl<Student> implements StudentDao {
    private static final String FIND_ALL_QUERY = "select b from Student b order by id asc";
    private static final String FIND_BY_EMAIL_QUERY = "select s from Student s where s.email = :email";
    private static final String DELETE_BY_ID_QUERY = "delete from Student where id = :id";
    private static final String COUNT_QUERY = "select count(id) from Student";

    @Autowired
    public StudentDaoImpl(EntityManager entityManager, @Value("${hibernate.batch_size}") int batchSize) {
        super(entityManager, FIND_ALL_QUERY, DELETE_BY_ID_QUERY, COUNT_QUERY, batchSize);
    }

    @Override
    public Optional<Student> findById(Integer id) {
        return Optional.ofNullable(entityManager.find(Student.class, id));
    }

    @Override
    @SuppressWarnings("unchecked")
    public Optional<Student> findByEmail(String email) {
        log.debug("Find student by email {}", email);

        return entityManager.createQuery(FIND_BY_EMAIL_QUERY)
                .setParameter("email", email)
                .getResultList().stream().findFirst();
    }
}
