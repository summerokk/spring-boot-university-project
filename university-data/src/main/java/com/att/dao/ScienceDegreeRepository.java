package com.att.dao;

import com.att.entity.ScienceDegree;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScienceDegreeRepository extends JpaRepository<ScienceDegree, Integer> {
}
