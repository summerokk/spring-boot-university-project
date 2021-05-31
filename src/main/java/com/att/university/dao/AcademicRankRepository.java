package com.att.university.dao;

import com.att.university.entity.AcademicRank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AcademicRankRepository extends JpaRepository<AcademicRank, Integer> {
}
