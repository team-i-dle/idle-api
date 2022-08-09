package com.bside.idle.membercriteria.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bside.idle.entity.MemberCriteria;

public interface MemberCriteriaRepository extends JpaRepository<MemberCriteria, Long> {

	@Query("select mc from MemberCriteria mc where mc.id in (:ids)")
	List<MemberCriteria> findByIds(@Param("ids") List<Long> ids);
}
